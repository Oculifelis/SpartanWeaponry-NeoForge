"""CurseForge banner and icon for Spartan Weaponry Unofficial, built only from art the mod already ships:
the title logo (src/main/resources/logo.png) on a dark background sampled from the mod's textures, and a
diamond greatsword texture scaled up front-on for the icon. Run from the repo root:
    python tools/make_banner.py   -> branding/banner_1280x400.png, banner_2560x800.png, icon_400x400.png
"""
import os

from PIL import Image, ImageDraw

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
RES = os.path.join(ROOT, "src", "main", "resources")
ITEM = os.path.join(RES, "assets", "spartan_weaponry_unofficial", "textures", "item")
OUT = os.path.join(ROOT, "branding")

TOP = (34, 30, 40)
BOTTOM = (14, 12, 18)
EDGE = (58, 52, 64)


def gradient(w, h):
    im = Image.new("RGBA", (w, h))
    d = ImageDraw.Draw(im)
    for y in range(h):
        t = y / max(1, h - 1)
        c = tuple(int(TOP[i] + (BOTTOM[i] - TOP[i]) * t) for i in range(3)) + (255,)
        d.line([(0, y), (w, y)], fill=c)
    return im


def banner(w, h):
    im = gradient(w, h)
    logo = Image.open(os.path.join(RES, "logo.png")).convert("RGBA")
    scale = min((w * 0.9) / logo.width, (h * 0.9) / logo.height)
    logo = logo.resize((int(logo.width * scale), int(logo.height * scale)), Image.NEAREST)
    im.alpha_composite(logo, ((w - logo.width) // 2, (h - logo.height) // 2))
    return im


def icon(size=400):
    im = gradient(size, size)
    sword = Image.open(os.path.join(ITEM, "diamond_greatsword.png")).convert("RGBA")
    k = (size * 3 // 4) // sword.width
    sword = sword.resize((sword.width * k, sword.height * k), Image.NEAREST)
    im.alpha_composite(sword, ((size - sword.width) // 2, (size - sword.height) // 2))
    d = ImageDraw.Draw(im)
    for i in range(8):
        d.rectangle([i, i, size - 1 - i, size - 1 - i], outline=EDGE + (255,) if i < 6 else BOTTOM + (255,))
    return im


if __name__ == "__main__":
    os.makedirs(OUT, exist_ok=True)
    banner(1280, 400).save(os.path.join(OUT, "banner_1280x400.png"))
    banner(2560, 800).save(os.path.join(OUT, "banner_2560x800.png"))
    icon().save(os.path.join(OUT, "icon_400x400.png"))
    print("wrote", OUT)
