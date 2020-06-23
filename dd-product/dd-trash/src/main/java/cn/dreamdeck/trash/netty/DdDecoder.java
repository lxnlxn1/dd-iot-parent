package cn.dreamdeck.trash.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 粘包处理
 */
public class DdDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        Object o = decode(ctx, in);
        if(o != null) {
            out.add(o);
        }
    }

    private Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {


        if(in.readableBytes() > Message.BYTE_SOCKET){
            in.skipBytes(in.readableBytes());
        }
        int beginReader;
        String head = ByteBufUtil.hexDump(in.readBytes(Message.BYTE_FRAME_HEAD));
        if(!Message.HEAD_FLAG.equalsIgnoreCase(head)) {
            ByteBuf byteBuf =in;

            return null;
        }else if(!Message.HEAD_FLAG1.equalsIgnoreCase(head)){

        }

            return in;


    }

    public static String convertByteBufToString(ByteBuf buf) {
        String str;

        if(buf.hasArray()) { // 处理堆缓冲区
            str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
        } else { // 处理直接缓冲区以及复合缓冲区
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            str = new String(bytes, 0, buf.readableBytes());
        }
        return str;
    }
    public static byte[] subBytes(byte[] b,int off,int length) {
        byte[] b1 = new byte[length];
        System.arraycopy(b, off, b1, 0, length);
        return b1;

    }
}
