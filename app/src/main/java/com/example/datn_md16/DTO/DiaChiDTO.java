    package com.example.datn_md16.DTO;

    import com.google.gson.annotations.SerializedName;

    public class DiaChiDTO {
        @SerializedName("_id")
        private String id;

        @SerializedName("ten")
        private String ten;

        @SerializedName("sdt")
        private String sdt;

        @SerializedName("diaChi")
        private String diaChi;

        // Các constructor và getter/setter
        public DiaChiDTO(String id, String ten, String sdt, String diaChi) {
            this.id = id;
            this.ten = ten;
            this.sdt = sdt;
            this.diaChi = diaChi;
        }

        public DiaChiDTO(String ten, String sdt, String diaChi) {
            this.ten = ten;
            this.sdt = sdt;
            this.diaChi = diaChi;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTen() {
            return ten;
        }

        public void setTen(String ten) {
            this.ten = ten;
        }

        public String getSdt() {
            return sdt;
        }

        public void setSdt(String sdt) {
            this.sdt = sdt;
        }

        public String getDiaChi() {
            return diaChi;
        }

        public void setDiaChi(String diaChi) {
            this.diaChi = diaChi;
        }

        @Override
        public String toString() {
            return "DiaChiDTO{" +
                    "id='" + id + '\'' +
                    ", ten='" + ten + '\'' +
                    ", sdt='" + sdt + '\'' +
                    ", diaChi='" + diaChi + '\'' +
                    '}';
        }
    }
