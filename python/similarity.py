from sentence_transformers import SentenceTransformer, util
import sys

if len(sys.argv) < 3:
    print("Usage: python script.py 'expected text' 'actual text'")
    sys.exit(1)

model = SentenceTransformer('all-MiniLM-L6-v2', cache_folder='./models')

expected = sys.argv[1]
actual = sys.argv[2]

emb1 = model.encode(expected, convert_to_tensor=True)
emb2 = model.encode(actual, convert_to_tensor=True)

score = util.cos_sim(emb1, emb2).item()

print(score)