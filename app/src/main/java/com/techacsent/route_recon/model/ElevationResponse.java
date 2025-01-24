package com.techacsent.route_recon.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ElevationResponse {

    /**
     * type : FeatureCollection
     * features : [{"type":"Feature","id":1,"geometry":{"type":"Point","coordinates":[90.3742,23.7461]},"properties":{"ele":-10,"index":-1,"tilequery":{"distance":0,"geometry":"polygon","layer":"contour"}}},{"type":"Feature","id":2,"geometry":{"type":"Point","coordinates":[90.3742,23.7461]},"properties":{"ele":0,"index":-1,"tilequery":{"distance":0,"geometry":"polygon","layer":"contour"}}},{"type":"Feature","id":3,"geometry":{"type":"Point","coordinates":[90.3742,23.7461]},"properties":{"ele":10,"index":1,"tilequery":{"distance":0,"geometry":"polygon","layer":"contour"}}}]
     */

    @SerializedName("type")
    private String type;
    @SerializedName("features")
    private List<FeaturesBean> features;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<FeaturesBean> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeaturesBean> features) {
        this.features = features;
    }

    public static class FeaturesBean {
        /**
         * type : Feature
         * id : 1
         * geometry : {"type":"Point","coordinates":[90.3742,23.7461]}
         * properties : {"ele":-10,"index":-1,"tilequery":{"distance":0,"geometry":"polygon","layer":"contour"}}
         */

        @SerializedName("type")
        private String type;
        @SerializedName("id")
        private int id;
        @SerializedName("geometry")
        private GeometryBean geometry;
        @SerializedName("properties")
        private PropertiesBean properties;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public GeometryBean getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryBean geometry) {
            this.geometry = geometry;
        }

        public PropertiesBean getProperties() {
            return properties;
        }

        public void setProperties(PropertiesBean properties) {
            this.properties = properties;
        }

        public static class GeometryBean {
            /**
             * type : Point
             * coordinates : [90.3742,23.7461]
             */

            @SerializedName("type")
            private String type;
            @SerializedName("coordinates")
            private List<Double> coordinates;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Double> getCoordinates() {
                return coordinates;
            }

            public void setCoordinates(List<Double> coordinates) {
                this.coordinates = coordinates;
            }
        }

        public static class PropertiesBean {
            /**
             * ele : -10
             * index : -1
             * tilequery : {"distance":0,"geometry":"polygon","layer":"contour"}
             */

            @SerializedName("ele")
            private int ele;
            @SerializedName("index")
            private int index;
            @SerializedName("tilequery")
            private TilequeryBean tilequery;

            public int getEle() {
                return ele;
            }

            public void setEle(int ele) {
                this.ele = ele;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public TilequeryBean getTilequery() {
                return tilequery;
            }

            public void setTilequery(TilequeryBean tilequery) {
                this.tilequery = tilequery;
            }

            public static class TilequeryBean {
                /**
                 * distance : 0
                 * geometry : polygon
                 * layer : contour
                 */

                @SerializedName("distance")
                private int distance;
                @SerializedName("geometry")
                private String geometry;
                @SerializedName("layer")
                private String layer;

                public int getDistance() {
                    return distance;
                }

                public void setDistance(int distance) {
                    this.distance = distance;
                }

                public String getGeometry() {
                    return geometry;
                }

                public void setGeometry(String geometry) {
                    this.geometry = geometry;
                }

                public String getLayer() {
                    return layer;
                }

                public void setLayer(String layer) {
                    this.layer = layer;
                }
            }
        }
    }
}
