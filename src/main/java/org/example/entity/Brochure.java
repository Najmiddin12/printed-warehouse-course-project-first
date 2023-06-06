package org.example.entity;

import java.util.Objects;

public class Brochure extends Product<Brochure>{
    private String size;
    private String targetAudience;
    private String paperType;


    public String getSize() { return size; }

    public Brochure setSize(String size) {
        this.size = size;
        return  this;
    }

    public String getTargetAudience() { return targetAudience; }

    public Brochure setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
        return this;
    }

    public String getPaperType() { return paperType; }

    public Brochure setPaperType(String paperType) {
        this.paperType = paperType;
        return this;
    }

    @Override
    public String toString() {
        return "Brochure{" + String.join(", ",
                commonFields(),
                "size=" + size,
                "targetAudience=" + targetAudience,
                "paperType=" + paperType
        ) + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Brochure brochure = (Brochure) o;
        return paperType == brochure.paperType && Objects.equals(size, brochure.size) && Objects.equals(targetAudience, brochure.targetAudience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), size, targetAudience, paperType);
    }
}
