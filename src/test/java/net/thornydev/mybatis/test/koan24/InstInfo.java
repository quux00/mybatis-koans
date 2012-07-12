package net.thornydev.mybatis.test.koan24;

import java.util.Arrays;

/** bean which uses auto-insert */
public class InstInfo {

	/**
	 * user data payload
	 */
	private byte[] message;

	/**
	 * external uuid
	 */
	private String sourceId;

	/**
	 * internal uuid to be auto-generated
	 */
	private Long targetId;

	transient private long timeStamp;

	//

	@Override
	public boolean equals(final Object other) {
		if (other instanceof InstInfo) {
			final InstInfo that = (InstInfo) other;
			return this.equalsIdentity(that)
					&& Arrays.equals(this.message, that.message);

		}
		return false;
	}

	public boolean equalsIdentity(final InstInfo that) {
		final String thisId = "" + this.sourceId + this.targetId;
		final String thatId = "" + that.sourceId + that.targetId;
		return thisId.equals(thatId);
	}

	public boolean equalsSourceId(final String id) {
		return equalsSafe(sourceId, id);
	}

	public boolean equalsTargetId(final String id) {
		return equalsSafe(targetId, id);
	}

	protected static boolean equalsSafe(final Object one, final Object two) {
		if (one == two) {
			return true;
		}
		if (one == null) {
			return false;
		}
		return one.equals(two);
	}

	public byte[] getMessage() {
		return message;
	}

	public String getSourceId() {
		return sourceId;
	}

	//

	public Long getTargetId() {
		return targetId;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public boolean hasSourceId() {
		return sourceId != null;
	}

	public boolean hasTargetId() {
		return targetId != null;
	}

	public boolean isValid() {
		return hasSourceId() && hasTargetId();
	}

	public void setMessage(final byte[] message) {
		this.message = message;
	}

	public void setSourceId(final String nativeId) {
		this.sourceId = nativeId;
	}

	public void setTargetId(final Long marketId) {
		this.targetId = marketId;
	}

	public void setTimeStamp(final long timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		final StringBuilder text = new StringBuilder(128);
		text.append(" sourceId=");
		text.append(sourceId);
		text.append(" targetId=");
		text.append(targetId);
		return text.toString();
	}

}
