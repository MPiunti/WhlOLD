package org.neo4j.batchimport.importer;

import java.text.ParseException;

import org.neo4j.batchimport.utils.DateToTimeUnits;

public enum Type {
    ID {
        @Override
        public Object convert(String value) {
            return Long.parseLong(value);
        }
        public boolean isProperty() { return false; }
    },
    LABEL {
        @Override
        public Object convert(String value) {
            return value.trim().split("\\s*,\\s*");
        }
        public boolean isProperty() { return false; }
    },
    BOOLEAN {
        @Override
        public Object convert(String value) {
            return Boolean.valueOf(value);
        }
    },
    INT {
        @Override
        public Object convert(String value) {
            return Integer.valueOf(value);
        }
    },
    LONG {
        @Override
        public Object convert(String value) {
            return Long.valueOf(value);
        }
    },
    MILLIS {
    	 @Override
         public Object convert(String value) {
    		 Long millis = null;
    		 try {
				millis = DateToTimeUnits.getTimeMillis(value);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				millis = 0L;
				e.printStackTrace();
			}
            return millis;
         }
    },
    TIMESTAMP {
   	 @Override
        public Object convert(String value) {
   		 Long timestamp = null;
   		 try {
				timestamp = DateToTimeUnits.getTimeStamp(value);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				timestamp = 0L;
				e.printStackTrace();
			}
           return timestamp;
        }
   },
    DOUBLE {
        @Override
        public Object convert(String value) {
            return Double.valueOf(value);
        }
    },
    FLOAT {
        @Override
        public Object convert(String value) {
            return Float.valueOf(value);
        }
    },
    BYTE {
        @Override
        public Object convert(String value) {
            return Byte.valueOf(value);
        }
    },
    SHORT {
        @Override
        public Object convert(String value) {
            return Short.valueOf(value);
        }
    },
    CHAR {
        @Override
        public Object convert(String value) {
            return value.charAt(0);
        }
    },
    STRING {
        @Override
        public Object convert(String value) {
            return value;
        }
    },
    BOOLEAN_ARRAY {
        @Override
        public Object convert(String value) {
            String[] strArray = value.split(",");
            boolean[] booleanArray = new boolean[strArray.length];
            for(int i = 0; i < strArray.length; i++) {
                booleanArray[i] = Boolean.valueOf(strArray[i]);
            }
            return booleanArray;
        }
    },
    INT_ARRAY {
        @Override
        public Object convert(String value) {
            String[] strArray = value.split(",");
            int[] intArray = new int[strArray.length];
            for(int i = 0; i < strArray.length; i++) {
                intArray[i] = Integer.parseInt(strArray[i]);
            }
            return intArray;
        }
    },
    LONG_ARRAY {
        @Override
        public Object convert(String value) {
            String[] strArray = value.split(",");
            long[] longArray = new long[strArray.length];
            for(int i = 0; i < strArray.length; i++) {
                longArray[i] = Long.parseLong(strArray[i]);
            }
            return longArray;
        }
    },
    DOUBLE_ARRAY {
        @Override
        public Object convert(String value) {
            String[] strArray = value.split(",");
            double[] doubleArray = new double[strArray.length];
            for(int i = 0; i < strArray.length; i++) {
                doubleArray[i] = Double.parseDouble(strArray[i]);
            }
            return doubleArray;
        }
    },
    FLOAT_ARRAY {
        @Override
        public Object convert(String value) {
            String[] strArray = value.split(",");
            float[] floatArray = new float[strArray.length];
            for(int i = 0; i < strArray.length; i++) {
                floatArray[i] = Float.parseFloat(strArray[i]);
            }
            return floatArray;
        }
    },
    BYTE_ARRAY {
        @Override
        public Object convert(String value) {
            String[] strArray = value.split(",");
            byte[] byteArray = new byte[strArray.length];
            for(int i = 0; i < strArray.length; i++) {
                byteArray[i] = Byte.parseByte(strArray[i]);
            }
            return byteArray;
        }
    },
    SHORT_ARRAY {
        @Override
        public Object convert(String value) {
            String[] strArray = value.split(",");
            short[] shortArray = new short[strArray.length];
            for(int i = 0; i < strArray.length; i++) {
                shortArray[i] = Short.parseShort(strArray[i]);
            }
            return shortArray;
        }
    },
    CHAR_ARRAY {
        @Override
        public Object convert(String value) {
            String[] strArray = value.split(",");
            char[] charArray = new char[strArray.length];
            for(int i = 0; i < strArray.length; i++) {
                charArray[i] = strArray[i].charAt(0);
            }
            return charArray;
        }
    },
    STRING_ARRAY {
        @Override
        public Object convert(String value) {
            return value.split(",");
        }
    };

    public static Type fromString(String typeString) {
        if (typeString==null || typeString.isEmpty()) return Type.STRING;
        try {
            return valueOf(typeString.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Unknown Type "+typeString);
        }
    }

    public abstract Object convert(String value);

    public boolean isProperty() { return true; }
}
