package cn.edu.thu.tsfile.timeseries.write.record;

import java.io.IOException;
import java.math.BigDecimal;

import cn.edu.thu.tsfile.common.exception.UnSupportedDataTypeException;
import cn.edu.thu.tsfile.common.utils.Binary;
import cn.edu.thu.tsfile.file.metadata.enums.TSDataType;
import cn.edu.thu.tsfile.timeseries.utils.StringContainer;
import cn.edu.thu.tsfile.timeseries.write.record.datapoint.BigDecimalDataPoint;
import cn.edu.thu.tsfile.timeseries.write.record.datapoint.DoubleDataPoint;
import cn.edu.thu.tsfile.timeseries.write.record.datapoint.FloatDataPoint;
import cn.edu.thu.tsfile.timeseries.write.series.ISeriesWriter;
import cn.edu.thu.tsfile.timeseries.write.record.datapoint.BooleanDataPoint;
import cn.edu.thu.tsfile.timeseries.write.record.datapoint.EnumDataPoint;
import cn.edu.thu.tsfile.timeseries.write.record.datapoint.IntDataPoint;
import cn.edu.thu.tsfile.timeseries.write.record.datapoint.LongDataPoint;
import cn.edu.thu.tsfile.timeseries.write.record.datapoint.StringDataPoint;

/**
 * This is a abstract class representing a data point. DataPoint consists of a
 * measurement id and a data type. subclass of DataPoint need override method
 * {@code write(long time, ISeriesWriter writer)} .Every subclass has its data
 * type and overrides a setting method for its data type.
 * 
 * @author kangrong
 *
 */
public abstract class DataPoint {
	protected final TSDataType type;
	protected final String measurementId;

	public DataPoint(TSDataType type, String measurementId) {
		this.type = type;
		this.measurementId = measurementId;
	}

	/**
	 * Construct one data point with data type and value
	 * @param dataType
	 * @param measurementId
	 * @param value
	 * @return
	 */
	public static DataPoint getDataPoint(TSDataType dataType, String measurementId, String value) {
		
		DataPoint dataPoint = null;
		switch (dataType) {
		case INT32:
			dataPoint = new IntDataPoint(measurementId, Integer.valueOf(value));
			break;
		case INT64:
			dataPoint = new LongDataPoint(measurementId, Long.valueOf(value));
			break;
		case FLOAT:
			dataPoint = new FloatDataPoint(measurementId, Float.valueOf(value));
			break;
		case DOUBLE:
			dataPoint = new DoubleDataPoint(measurementId, Double.valueOf(value));
			break;
		case BOOLEAN:
			dataPoint = new BooleanDataPoint(measurementId, Boolean.valueOf(value));
			break;
		case BYTE_ARRAY:
			dataPoint = new StringDataPoint(measurementId, new Binary(value));
			break;
		case BIGDECIMAL:
			dataPoint = new BigDecimalDataPoint(measurementId, new BigDecimal(value));
			break;
		case ENUMS:
			dataPoint = new EnumDataPoint(measurementId, Integer.valueOf(value));
			break;
		default:
			throw new UnSupportedDataTypeException("This data type is not supoort -" + dataType);
		}
		return dataPoint;
	}

	/**
	 * write to seriesWriter and return the series name
	 * 
	 * @param time
	 * @param writer
	 * @return
	 */
	public abstract void write(long time, ISeriesWriter writer) throws IOException;

	public String getMeasurementId() {
		return measurementId;
	}

	public abstract Object getValue();

	public TSDataType getType() {
		return type;
	}

	@Override
	public String toString() {
		StringContainer sc = new StringContainer(" ");
		sc.addTail("{measurement id:", measurementId, "type:", type, "value:", getValue(), "}");
		return sc.toString();
	}

	public void setInteger(int value) {
		throw new UnsupportedOperationException("set Integer not support in DataPoint");
	}

	public void setLong(long value) {
		throw new UnsupportedOperationException("set Long not support in DataPoint");
	}

	public void setBoolean(boolean value) {
		throw new UnsupportedOperationException("set Boolean not support in DataPoint");
	}

	public void setFloat(float value) {
		throw new UnsupportedOperationException("set Float not support in DataPoint");
	}

	public void setDouble(double value) {
		throw new UnsupportedOperationException("set Double not support in DataPoint");
	}

	public void setString(Binary value) {
		throw new UnsupportedOperationException("set String not support in DataPoint");
	}

	public void setBigDecimal(BigDecimal value) {
		throw new UnsupportedOperationException("set BigDecimal not support in DataPoint");
	}
}
