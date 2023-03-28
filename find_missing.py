from xml.etree.ElementTree import parse
import os

root = parse("app/src/main/res/xml/grayscale_icon_map.xml").getroot()
svgs = os.listdir("svgs/")
drawables = []
for i in root:
    drawable = str(i.attrib.get("drawable", None)) + ".svg"
    drawables.append(drawable)

for i in svgs:
    if i not in drawables:
            print(i)