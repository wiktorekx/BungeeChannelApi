package pl.wiktorekx.bungeechannelapi.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class BMessage {
    private final ByteBuf buf;

    public BMessage() {
        buf = Unpooled.buffer();
    }

    public BMessage(byte @NotNull [] bytes){
        buf = Unpooled.wrappedBuffer(Objects.requireNonNull(bytes));
    }

    public BMessage(@NotNull ByteBuf buf){
        this.buf = Objects.requireNonNull(buf).duplicate();
    }

    public BMessage(@NotNull BMessage message) {
        this.buf = Objects.requireNonNull(message).getBuf();
    }

    public @NotNull BMessage writeBytes(byte @NotNull [] bytes) {
        buf.writeBytes(bytes);
        return this;
    }

    public void readBytes(byte @NotNull [] bytes) throws IndexOutOfBoundsException {
        buf.readBytes(bytes);
    }

    public @NotNull BMessage writeByte(byte value) {
        buf.writeByte(value);
        return this;
    }

    public byte readByte() throws IndexOutOfBoundsException {
        return buf.readByte();
    }

    public @NotNull BMessage writeShort(short value) {
        buf.writeShort(value);
        return this;
    }

    public short readShort() throws IndexOutOfBoundsException {
        return buf.readShort();
    }

    public @NotNull BMessage writeInt(int value){
        buf.writeInt(value);
        return this;
    }

    public int readInt() throws IndexOutOfBoundsException {
        return buf.readInt();
    }

    public @NotNull BMessage writeLong(long value){
        buf.writeLong(value);
        return this;
    }

    public long readLong() throws IndexOutOfBoundsException {
        return buf.readLong();
    }

    public @NotNull BMessage writeFloat(float value){
        buf.writeFloat(value);
        return this;
    }

    public float readFloat() throws IndexOutOfBoundsException {
        return buf.readFloat();
    }

    public @NotNull BMessage writeDouble(double value){
        buf.writeDouble(value);
        return this;
    }

    public double readDouble() throws IndexOutOfBoundsException {
        return buf.readDouble();
    }

    public @NotNull BMessage writeBoolean(boolean value){
        buf.writeBoolean(value);
        return this;
    }

    public boolean readBoolean() throws IndexOutOfBoundsException {
        return buf.readBoolean();
    }

    public @NotNull BMessage writeChar(char value){
        buf.writeChar(value);
        return this;
    }

    public char readChar() throws IndexOutOfBoundsException {
        return buf.readChar();
    }

    public @NotNull BMessage writeString(@NotNull String value){
        Objects.requireNonNull(value);
        writeShort((short) value.length());
        writeBytes(value.getBytes(StandardCharsets.UTF_8));
        return this;
    }

    @NotNull
    public String readString(){
        byte[] bytes = new byte[readShort()];
        readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public byte @NotNull [] toByteArray() {
        return buf.array();
    }

    @NotNull
    public ByteBuf getBuf() {
        return buf.duplicate();
    }

    @Override
    @SneakyThrows
    @NotNull
    public BMessage clone() {
        return new BMessage((byte[]) super.clone());
    }
}
