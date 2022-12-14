public class Vector {
    private double[] doubElements;

    public Vector(double[] _elements) {
        //TODO Task 1.1
        this.doubElements = _elements;
    }

    public double getElementatIndex(int _index) {
        //TODO Task 1.2
        if(_index < doubElements.length) {
            return doubElements[_index];
        }
        return -1;
    }

    public void setElementatIndex(double _value, int _index) {
        //TODO Task 1.3
        if (_index < doubElements.length) {
            doubElements[_index] = _value;

        } else if (_index >= doubElements.length) {
            doubElements[doubElements.length - 1] = _value;
        }
    }

    public double[] getAllElements() {
        //TODO Task 1.4
        return doubElements;
    }

    public int getVectorSize() {
        //TODO Task 1.5
        return doubElements.length;
    }

    public Vector reSize(int _size) {
        //TODO Task 1.6
        int span = this.doubElements.length;
        double[] sizeArray = {0};

        if(span == _size || _size <= 0) {
            return this;
        }

        else if(_size < span) {
            sizeArray = new double[_size];
            int i = 0;
            while (i < _size) {
                sizeArray[i] = doubElements[i];
                i++;
            }
        }

        else if(_size > span) {
            sizeArray = new double[_size];
            int i = 0;
            while (i < span) {
                sizeArray[i] = doubElements[i];
                int j = span;
                while(j < _size) {
                    sizeArray[j] = -1.0;
                    j++;
                }
                i++;
            }
        }
        return new Vector(sizeArray);
    }

    public Vector add(Vector _v) {
        //TODO Task 1.7
        double[] addArray;

        if (_v.doubElements.length == this.doubElements.length) {
            double[] addArrayVector = new double[this.doubElements.length];
            int i = 0;
            for (double index : this.doubElements) {
                addArrayVector[i] = index + _v.doubElements[i];
                i++;
            }
            this.doubElements = addArrayVector;
            return this;
        }

        else if (_v.doubElements.length < this.doubElements.length) {
            _v = _v.reSize(this.doubElements.length);
            int i = 0;
            for (double index : this.doubElements) {
                setElementatIndex(_v.doubElements[i] + index, i);
                i++;
            }
            return this;

        } else if (_v.doubElements.length > this.doubElements.length) {
            addArray = new double[_v.doubElements.length];
            Vector addResize = this.reSize(_v.doubElements.length);
            int i = 0;
            for (double index : addResize.doubElements) {
                double x = index + _v.doubElements[i];
                addArray[i] = x;
                i++;
            }
            return new Vector(addArray);

        }
        return null;
    }

    public Vector subtraction(Vector _v) {
        //TODO Task 1.8
        double[] subArray;

        if (_v.doubElements.length == this.doubElements.length) {
            double[] subArrayVector = new double[this.doubElements.length];
            int i = 0;
            for (double index : this.doubElements) {
                subArrayVector[i] = index - _v.doubElements[i];
                i++;
            }
            this.doubElements = subArrayVector;
            return this;
        }

        else if (_v.doubElements.length < this.doubElements.length) {
            _v = _v.reSize(this.doubElements.length);
            int i = 0;
            for (double index : this.doubElements) {
                this.setElementatIndex(index - _v.doubElements[i], i);
                i++;
            }
            return this;
        }

        else if (_v.doubElements.length > this.doubElements.length) {
            subArray = new double[_v.doubElements.length];
            Vector subResize = this.reSize(_v.doubElements.length);
            int i = 0;
            for (double index : subResize.doubElements) {
                double x = index - _v.doubElements[i];
                subArray[i] = x;
                i++;
            }
            return new Vector(subArray);
        }
        return null;
    }

    public double dotProduct(Vector _v) {
        //TODO Task 1.9
        double[] dotProductArray;
        double dotProduct = 0;

        if (this.doubElements.length == _v.doubElements.length) {
            dotProductArray = new double[this.doubElements.length];

            int i = 0;
            for (double index : this.doubElements) {
                dotProductArray[i] = index * _v.doubElements[i];
                i++;
            }
            for (double index : dotProductArray) {
                dotProduct += index;
            }
        }

        else if (_v.doubElements.length < this.doubElements.length) {
            _v = _v.reSize(this.doubElements.length);
            int i = 0;
            for (double index : this.doubElements) {
                setElementatIndex(index * _v.doubElements[i], i);
                i++;
            }
            for (double index : this.doubElements) {
                dotProduct += index;
            }
        }

        else if (_v.doubElements.length > this.doubElements.length) {
            dotProductArray = new double[_v.doubElements.length];
            Vector dotVector = this.reSize(_v.doubElements.length);
            int i = 0;
            for (double index : dotVector.doubElements) {
                double j = index * _v.doubElements[i];
                dotProductArray[i] = j;
                i++;
            }
            for (double index : dotProductArray) {
                dotProduct += index;
            }
        }
        return dotProduct;
    }

    public double cosineSimilarity(Vector _v) {
        //TODO Task 1.10
        double num;
        double den;
        double cosSim = 0;
        double den1 = 0;
        double den2 = 0;

        if (this.doubElements.length < _v.getVectorSize()) {
            Vector cosVec = this.reSize(_v.getVectorSize());
            num = cosVec.dotProduct(_v);
            for (double index : cosVec.doubElements) {
                den1 += Math.pow(index, 2);
            }
            for (double index : _v.doubElements) {
                den2 += Math.pow(index, 2);
            }
            den = (Math.sqrt(den1) * Math.sqrt(den2));
            cosSim = num / den;
        }

        else if (this.doubElements.length > _v.getVectorSize()) {
            _v = _v.reSize(this.getVectorSize());
            num = this.dotProduct(_v);
            for (double element : this.doubElements) {
                den1 += Math.pow(element, 2);
            }
            for (double element : _v.doubElements) {
                den2 += Math.pow(element, 2);
            }
            den = (Math.sqrt(den1) * Math.sqrt(den2));
            cosSim = num / den;
        }

        else if (this.doubElements.length == _v.getVectorSize()) {
            num = this.dotProduct(_v);
            for (double element : this.doubElements) {
                den1 += Math.pow(element, 2);
            }
            for (double element : _v.doubElements) {
                den2 += Math.pow(element, 2);
            }
            den = (Math.sqrt(den1) * Math.sqrt(den2));
            cosSim = num / den;
        }
        return cosSim;
    }

    @Override
    public boolean equals(Object _obj) {
        Vector v = (Vector) _obj;
        boolean boolEquals = true;
        //TODO Task 1.11
        if(this.doubElements.length == v.doubElements.length) {
            int i = 0;
            for (double element : this.doubElements) {
                if (element != v.doubElements[i]) {
                    return false;
                } else {
                    break;
                }
            }
        }
        else if(this.doubElements.length != v.doubElements.length) {
            return false;
        }
        return boolEquals;
    }

    @Override
    public String toString() {
        StringBuilder mySB = new StringBuilder();
        for (int i = 0; i < this.getVectorSize(); i++) {
            mySB.append(String.format("%.5f", doubElements[i])).append(",");
        }
        mySB.delete(mySB.length() - 1, mySB.length());
        return mySB.toString();
    }
}
