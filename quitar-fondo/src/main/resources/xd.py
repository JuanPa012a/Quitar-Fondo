from rembg import remove
import sys
input_path = sys.argv[1]
output_path = sys.argv[2]

with open(input_path, "rb") as inp, open(output_path, "wb") as out:
    out.write(remove(inp.read()))

print(f"Imagen procesada y guardada en: {output_path}")