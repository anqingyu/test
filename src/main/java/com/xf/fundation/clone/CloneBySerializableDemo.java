package com.xf.fundation.clone;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.*;
import java.util.Objects;

/**
 * 通过序列化的方式实现深克隆
 */
@Data
@EqualsAndHashCode
public class CloneBySerializableDemo implements Serializable {
    private static final long serialVersionUID = 3386047546634926962L;

    private int id;
    private Name name;

    public static void main(String[] args) {
        CloneBySerializableDemo cloneBySerializableDemo = new CloneBySerializableDemo();
        Name name = new Name();
        name.setName("张 三");
        cloneBySerializableDemo.setName(name);
        cloneBySerializableDemo.setId(1);

        CloneBySerializableDemo clone = cloneBySerializableDemo.myClone();
        // 地址比较：false，不是同一个对象
        System.out.println(clone == cloneBySerializableDemo);
        System.out.println(clone.equals(cloneBySerializableDemo));
    }

    public CloneBySerializableDemo myClone(){
        CloneBySerializableDemo instance = null;
        try {
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ObjectOutputStream oos=new ObjectOutputStream(bos);
            oos.writeObject(this);
            ByteArrayInputStream bis=new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois=new ObjectInputStream(bis);
            instance=(CloneBySerializableDemo)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return instance;
    }
}

class Name implements Serializable {
    private static final long serialVersionUID = 872390113109L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Name{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}