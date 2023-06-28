package com.api.monitormall.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 113409957L;

    public static final QProduct product = new QProduct("product");

    public final StringPath brand = createString("brand");

    public final BooleanPath dp = createBoolean("dp");

    public final BooleanPath dvi = createBoolean("dvi");

    public final BooleanPath hdmi = createBoolean("hdmi");

    public final StringPath img01 = createString("img01");

    public final StringPath img02 = createString("img02");

    public final StringPath img03 = createString("img03");

    public final StringPath img04 = createString("img04");

    public final StringPath img05 = createString("img05");

    public final NumberPath<Double> inch = createNumber("inch", Double.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final BooleanPath speaker = createBoolean("speaker");

    public final BooleanPath usb = createBoolean("usb");

    public final BooleanPath vga = createBoolean("vga");

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

