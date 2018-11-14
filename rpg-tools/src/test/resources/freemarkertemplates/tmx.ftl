<?xml version="1.0" encoding="UTF-8"?>
<map version="1.2" tiledversion="1.2.0" orientation="orthogonal" renderorder="right-down"
     width="${map.width}" height="${map.height}"
     tilewidth="32" tileheight="32" infinite="0" nextlayerid="6" nextobjectid="1">
    <properties>
        <property name="name" value="${map.name}"/>
    </properties>
    <tileset firstgid="1" source="${map.tileId?replace(".png", ".tsx")}"/>
    <layer id="0" name="bg" width="${map.width}" height="${map.height}">
        <data encoding="csv">
            ${bgLayerStr}
        </data>
    </layer>

    <layer id="1" name="walk" width="${map.width}" height="${map.height}">
        <data encoding="csv">
            ${walkLayerStr}
        </data>
    </layer>
</map>
