package pw.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.function.BooleanSupplier;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import pw.base.PWBaseTest;

/**
 * Author Tapan, Feb 26
 * 
 */
public class PWUtilities {
	public static String getRunFolder() {
		String folder = System.getProperty("RUN_FOLDER");
		if (folder == null) {
			throw new IllegalStateException("RUN_FOLDER not initialized yet. Listener onStart() did not execute.");
		}
		return folder;
	}

	public static void retryUntil(BooleanSupplier condition, Duration timeout, Duration pollInterval) {
		long end = System.currentTimeMillis() + timeout.toMillis();
		while (System.currentTimeMillis() < end) {
			if (condition.getAsBoolean()) {
				return;
			}
			try {
				Thread.sleep(pollInterval.toMillis());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}
		}
		throw new RuntimeException("Condition not met within timeout");
	}

	public static void cleanOldFolders(String targetPath, int daysOld) throws Exception {
		Path rootPath = Paths.get(targetPath);
		if (!Files.exists(rootPath)) {
			return;
		}
		LocalDateTime boundary = LocalDateTime.now().minusDays(daysOld);
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootPath);
		for (Path folder : directoryStream) {
			// Skip files, process only directories
			if (!Files.isDirectory(folder))
				continue;
			BasicFileAttributes attr = Files.readAttributes(folder, BasicFileAttributes.class);
			LocalDateTime lastWrite = attr.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDateTime();
			if (lastWrite.isBefore(boundary)) {
				// Create ZIP file inside same directory
				String zipName = folder.getFileName().toString() + "_backup_" + System.currentTimeMillis() + ".zip";
				Path zipPath = rootPath.resolve(zipName);
				zipFolder(folder, zipPath);
				deleteFolder(folder);
//				System.out.println("Processed: " + folder.getFileName());
			}
		}
	}

	// Zip a folder
	private static void zipFolder(Path sourceFolder, Path zipPath) throws Exception {
		try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipPath))) {
			Files.walk(sourceFolder).forEach(path -> {
				try {
					String zipEntryName = sourceFolder.relativize(path).toString();
					if (Files.isDirectory(path))
						return; // skip folder entries
					zos.putNextEntry(new ZipEntry(zipEntryName));
					Files.copy(path, zos);
					zos.closeEntry();
				} catch (IOException e) {
					throw new RuntimeException("Error zipping: " + path, e);
				}
			});
		}
	}

	// Delete folder recursively
	private static void deleteFolder(Path folder) throws Exception {
		Files.walk(folder).sorted((a, b) -> b.compareTo(a)) // delete children first
				.forEach(path -> {
					try {
						Files.delete(path);
					} catch (IOException e) {
						throw new RuntimeException("Failed to delete: " + path, e);
					}
				});
	}

	/**
	 * Tapan Gandhi 18 June 25
	 * 
	 * @return
	 */
	public static String getExecutionID() {
		String uniq = PWUtilities.propsReadWrite("data/addmaster.properties", "get", "executionID", "");
		try {
			String executionRunTime = PWBaseTest.mapAllVariables.get("executionRunTime");
			String holdNewData = PWBaseTest.mapAllVariables.get("holdNewData");
			if (uniq.length() == 12) {
				String date1 = executionRunTime.substring(0, 6);
				String date2 = uniq.substring(0, 6);
				long days = 0;
				try {
					days = getDateDifference(date1, date2);
				} catch (Exception e) {
				}
				int nthresld = Integer.parseInt(holdNewData);
				if (nthresld > 0 && days <= nthresld) {
					executionRunTime = uniq;
//					System.out.println("Continuing with Existing Master Data due to threshold value: " + date1 + " - "
//							+ date2 + " - Threshold : " + nthresld + ", with Exec. ID: " + executionRunTime);
				} else {
//					try {
//						boolean deleted = Files.deleteIfExists(Path.of("data", "addmaster.properties"));
//						if (deleted) {
//							System.out.println("Deleted successfully");
//						} else {
//							System.out.println("File not found");
//						}
//					} catch (IOException e) {
//						System.err.println("Deletion failed: " + e.getMessage());
//					}
					uniq = new SimpleDateFormat("ddMMyyHHmmss").format(new Date());
					executionRunTime = uniq;
					propsReadWrite("data/addmaster.properties", "set", "executionID", uniq);
					System.out.println("Created NEW EXEC ID IN data/addmaster.properties as Exceeds Threshold value: "
							+ date1 + " - " + date2 + " - ThreSld: " + nthresld + ", new Exec. ID: "
							+ executionRunTime);
				}
				PWBaseTest.mapAllVariables.put("executionRunTime", executionRunTime);

			} else {
				Files.deleteIfExists(Path.of("data", "addmaster.properties"));
				System.out.println("Deleted data\\addmaster.properties file.");
			}
		} catch (Exception e) {
			System.out.println("Error while getExecutionID :" + e.getMessage());
		}
		return uniq;
	}

	/**
	 * Tapan Gandhi 18 June 25
	 */
	static long getDateDifference(String date1, String date2) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
		LocalDate d1 = LocalDate.parse(date1, formatter);
		LocalDate d2 = LocalDate.parse(date2, formatter);
		return ChronoUnit.DAYS.between(d2, d1); // positive or negative
	}

	/**
	 * this method reads a value from a properties file. Author: Tapan Gandhi
	 * 
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static String getConfigValue(String fileName, String key) {
		File configFile = new File(fileName);
		String value = "";
		if (!configFile.exists()) {
			return "";
		}
		try (FileInputStream fis = new FileInputStream(configFile)) {
			Properties props = new Properties();
			props.load(fis);
			value = props.getProperty(key);
			if (value == null) {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
		return value;
	}

	/**
	 * This method writes or updates a key-value pair in a properties file. If the
	 * file doesn't exist, it will be created. If the key already exists, its value
	 * will be updated.
	 */
	public static void writeToPropsFile(String filePath, String key, String value) {
		Properties props = new Properties();
		File file = new File(filePath);
		try { // If file exists, load existing properties
			if (file.exists()) {
				try (FileInputStream fis = new FileInputStream(file)) {
					props.load(fis);
				}
			} else { // Ensure parent directory exists
				file.getParentFile().mkdirs();
				file.createNewFile();
			} // Set / update property
			props.setProperty(key, value);
			// Store back
			try (FileOutputStream fos = new FileOutputStream(file)) {
				props.store(fos, new Date().toString());
			}
		} catch (Exception e) {
			System.out.println("writeToPropsFile Error: " + e.getMessage());
		}
	}

	/**
	 * Builds a full path by combining two or more directory or file parts. Example:
	 * buildPath("C:\\base", "run123", "report"); Tapan Oct 25
	 * 
	 * @param pathParts One or more parts of the path (must include at least one)
	 * @return Combined Path object
	 * @throws IllegalArgumentException if no parts are provided
	 */
	public static Path getAbsolutePath(String... pathParts) {
		if (pathParts == null || pathParts.length == 0) {
			throw new IllegalArgumentException("At least one path part must be provided");
		}
		// Ensure no null elements
		for (String part : pathParts) {
			Objects.requireNonNull(part, "Path part cannot be null");
		}
		return Paths.get(pathParts[0], java.util.Arrays.copyOfRange(pathParts, 1, pathParts.length));
	}

	/**
	 * Tapan Gandhi 18 June 25
	 * 
	 * @param path     data\\addmaster.properties
	 * @param getPut   set or get
	 * @param keyWord  executionid
	 * @param keyValue done,datetime unique
	 * @return
	 */
	public static String propsReadWrite(String path, String getPut, String keyWord, String keyValue) {
		Properties props = new Properties();
		File file = new File(path);
		String executionRunTime = PWBaseTest.mapAllVariables.get("executionRunTime");
		if (getPut.equalsIgnoreCase("set")) { // Load existing props if file exists
			if (file.exists()) {
				try (FileInputStream fRead = new FileInputStream(file)) {
					props.load(fRead);
				} catch (Exception e) {
					System.out.println("Error loading existing props: " + e.getMessage());
					return "Error loading existing props: " + e.getMessage();
				}
			} else {
				props.setProperty("executionID", executionRunTime);
			} // Set key-value pair
			props.setProperty(keyWord, keyValue);
			try (FileOutputStream fWrite = new FileOutputStream(file)) { // Write the properties to file (create or
																			// overwrite)
				props.store(fWrite, null);
			} catch (Exception e) {
				System.out.println("Error saving props: " + e.getMessage());
				return "Error saving props: " + e.getMessage();
			}
			return "true"; // success
		}
		if (getPut.equalsIgnoreCase("get")) {
			if (!file.exists()) {
				return "";
			}
			try (FileInputStream fRead = new FileInputStream(file)) {
				props.load(fRead);
				return props.getProperty(keyWord, ""); // return value or empty string
			} catch (Exception e) {
				System.out.println("Error reading props: " + e.getMessage());
				return "Error reading props: " + e.getMessage();
			}
		}
		return "";
	}

	/**
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(String path) {
		return path != null && new File(path).exists() && new File(path).delete();
	}
}// EOF4
