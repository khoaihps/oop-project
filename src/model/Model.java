package model;
public abstract class Model {
    protected String name;
    protected Model(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
// public abstract class Model {
//     protected String ten;
//     protected Model(String name) {
//         this.ten = name;
//     }
//     public String getTen() {
//         return ten;
//     }
// }
