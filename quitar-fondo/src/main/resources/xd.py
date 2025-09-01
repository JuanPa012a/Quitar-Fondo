from rembg import remove
from rembg import remove
from PIL import Image, ImageEnhance

import io, sys
input_path = sys.argv[1]
output_path = sys.argv[2]


with open(input_path, "rb") as inp:
    img_no_bg = remove(inp.read())

img = Image.open(io.BytesIO(img_no_bg)).convert("RGBA")

# 2. Mejoras básicas
img = ImageEnhance.Sharpness(img).enhance(1.8)   # nitidez
img = ImageEnhance.Contrast(img).enhance(1.2)    # contraste
img = ImageEnhance.Brightness(img).enhance(1.1)  # brillo

# 3. Guardar
img.save(output_path)
print("XDD")
print(f"Imagen procesada y guardada en: {output_path}")