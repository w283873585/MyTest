package vr.com.pojo;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

public class InterfaceEntityCodec implements Codec<InterfaceEntity>{

	private final CodecRegistry codecRegistry;
	
	public InterfaceEntityCodec(final CodecRegistry codecRegistry) {
		this.codecRegistry = codecRegistry;
	}
	
	@Override
	public void encode(BsonWriter writer, InterfaceEntity value, EncoderContext encoderContext) {
		writer.writeStartDocument();
			writer.writeName("url");
			writer.writeString(value.getUrl());
			writer.writeName("name");
			writer.writeString(value.getName());
			writer.writeName("desc");
			writer.writeString(value.getDesc());
			writer.writeStartArray("params");
				for (InterfaceParam param : value.getParams()) {
					Codec<InterfaceParam> raCodec = codecRegistry.get(InterfaceParam.class);
					encoderContext.encodeWithChildContext(raCodec, writer, param);
				}
			writer.writeEndArray();
			writer.writeStartArray("results");
				for (InterfaceParam param : value.getParams()) {
					Codec<InterfaceParam> raCodec = codecRegistry.get(InterfaceParam.class);
					encoderContext.encodeWithChildContext(raCodec, writer, param);
				}
			writer.writeEndArray();
		writer.writeEndDocument();
	}

	@Override
	public Class<InterfaceEntity> getEncoderClass() {
		return InterfaceEntity.class;
	}

	@Override
	public InterfaceEntity decode(BsonReader reader, DecoderContext decoderContext) {
		InterfaceEntity result = new InterfaceEntity(); 
		Codec<InterfaceParam> raCodec = codecRegistry.get(InterfaceParam.class);
		reader.readStartDocument();
			 reader.readName();
			 reader.readObjectId();
	         reader.readName();
	         result.setDesc(reader.readString());
	         reader.readName();
	         result.setName(reader.readString());
	         reader.readStartArray();
	         	List<InterfaceParam> params = new ArrayList<InterfaceParam>(); 
	         	while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
	         		params.add(raCodec.decode(reader, decoderContext));
	            }
	         	result.setParams(params);
	         reader.readEndArray();
	         reader.readStartArray();
	         List<InterfaceParam> results = new ArrayList<InterfaceParam>(); 
	         while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
	        	 results.add(raCodec.decode(reader, decoderContext));
	         }
	         result.setResults(results);
	         reader.readEndArray();
	         reader.readName();
	         result.setUrl(reader.readString());
	    reader.readEndDocument();
		return result;
	}
}
