package progmmo.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("map")
public class MapConfig {
    private int sectorSize;
    private int mapSize;

    public int getSectorSize() {
        return sectorSize;
    }

    public void setSectorSize(int sectorSize) {
        this.sectorSize = sectorSize;
    }

    public int getMapSize() {
        return mapSize;
    }

    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }
}
