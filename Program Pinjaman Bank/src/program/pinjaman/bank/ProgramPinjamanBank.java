package program.pinjaman.bank;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProgramPinjamanBank {
    BufferedReader userInput = new BufferedReader (new InputStreamReader(System.in));
    Scanner masuk = new Scanner(System.in);
    String user="root";
    String pwd="";
    String host="localhost";
    String db="tubes_pbo";
    String urlValue="";
        

    public static void main(String[] args) 
    {   
        ProgramPinjamanBank objectU = new ProgramPinjamanBank();
        objectU.login();    
        /*ProgramPinjamanBank objectT = new ProgramPinjamanBank();
        objectT.tampil();
        ProgramPinjamanBank objectE = new ProgramPinjamanBank();
        objectT.edit();
        ProgramPinjamanBank ObjectS = new ProgramPinjamanBank();
        ObjectS.searching();*/
    }       
    
    
    public void utama()
    {
        System.out.println("======UTAMA======");
        System.out.println(" 1.  INSERT      ");
        System.out.println(" 2.  TAMPIL      ");
        System.out.println(" 3.  EDIT        ");
        System.out.println(" 4.  SEARCHING   ");
        System.out.println(" 5.  DELETE      ");
        System.out.println(" 6.  KELUAR      ");
        System.out.println("");
        System.out.print("MASUKAN PILIHAN :");
        int pilihan = this.masuk.nextInt();
    
        switch (pilihan) {
        case 1: insert();
        break;
        case 2: tampil();
        break;
        case 3: edit();
        break;
        case 4: searching();
        break;
        case 5: delete();
        break;
        case 6: keluar();
        break;
        default : System.out.println("“Pilihan yang kamu masukkan salah”");
        utama();
        }
    }
    
    void login()
    {
        Scanner isi = new Scanner(System.in);
        System.out.print("Masukkan Username : ");
        String uname = isi.nextLine();
        System.out.print("Masukkan Password : ");
        String pwd = isi.nextLine();
        if(uname.equals("admin")&&pwd.equals("admin"))
        {
            utama();
        }
        else
        {
            System.out.println("Username atau Password Salah");
        }
    }
    
    void insert() 
    {
        System.out.println("“=====INSERT=====”");
        System.out.println(" 1.  PINJAMAN     ");
        System.out.println(" 2.  NASABAH      ");
        System.out.println(" 3.  KARYAWAN     ");
        System.out.println(" 4.  JAMINAN      ");
        System.out.println(" 5.  KEMBALI      ");
        System.out.println("");
        System.out.print("MASUKAN PILIHAN : ");
        int pilihan = this.masuk.nextInt();

        switch (pilihan) {
        case 1: insert_pinjaman();
        break;
        case 2: insert_nasabah();
        break;
        case 3: insert_karyawan();
        break;
        case 4: insert_jaminan();
        break;
        case 5: kembali();
        break;
        default : System.out.println("“pilihan yang kamu masukkan salah”");
        insert();
        }
    }
    
    void insert_pinjaman()    
    {
        //Menambah Objek IO
        BufferedReader userInput = new BufferedReader (new InputStreamReader(System.in));
        
        //Cek Error dengan try
        try{
            //Koneksi Database
            Class.forName("com.mysql.jdbc.Driver");
            urlValue="jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
            Connection conn = DriverManager.getConnection(urlValue);
            
            PreparedStatement pStatement = null;
            //Input Data
            String sql = "INSERT INTO pinjaman (CIF,Rekening,Saldo,Kode_Properti,Limit_kredit,jangka_waktu,total_angsuran,NIP) \n" +
                         "VALUES ((SELECT CIF FROM Nasabah WHERE CIF = ?),?,?,\n" +
                         "(SELECT Kode_properti FROM jaminan WHERE kode_properti = ?),?,?,?,\n" +
                         "(SELECT NIP FROM Karyawan WHERE Nama_Karyawan = ?))";
            
            pStatement = conn.prepareStatement(sql);
            
            //Input Kode MK
            System.out.print("Masukan CIF          : ");
            String CIF = userInput.readLine();
            pStatement.setString(1, CIF);
            
            //Input Nama MK
            System.out.print("Masukan Rekening: ");
            String Rekening = userInput.readLine();
            pStatement.setString(2, Rekening);
            
            //Input Nama MK
            System.out.print("Masukan Saldo       : ");
            String Saldo = userInput.readLine();
            pStatement.setString(3, Saldo);
            
            System.out.print("Masukan Kode Properti       : ");
            String Kode_properti = userInput.readLine();
            pStatement.setString(4, Kode_properti);
            
            System.out.print("Masukan Limit Kredit       : ");
            String Limit_kredit = userInput.readLine();
            pStatement.setString(5, Limit_kredit);
            
            System.out.print("Masukan Jangka Waktu      : ");
            String Jangka_waktu = userInput.readLine();
            pStatement.setString(6, Jangka_waktu);
            
            System.out.print("Masukan Angsuran      : ");
            String Total_angsuran = userInput.readLine();
            pStatement.setString(7, Total_angsuran);
            
             System.out.print("Masukan NIP          : ");
            String NIP = userInput.readLine();
            pStatement.setString(8, NIP);
            
            //Cek Data berhasil ditambah atau gagal
            int intBaris=pStatement.executeUpdate();
            if (intBaris>0){
                System.out.println("Berhasil menambahkan data");
            } else {
                System.out.println("Penambahan data gagal");
            }
            
            //Close koneksi
            pStatement.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver tidak ditemukan");
        } catch (IOException ex) {
            Logger.getLogger(ProgramPinjamanBank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void insert_nasabah() 
    {
        BufferedReader userInput = new BufferedReader (new InputStreamReader(System.in));
        
        //Cek Error dengan try
        try{
            //Koneksi Database
            Class.forName("com.mysql.jdbc.Driver");
            urlValue="jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
            Connection conn = DriverManager.getConnection(urlValue);
            
            PreparedStatement pStatement = null;
            //Input Data
            String sql = "INSERT INTO nasabah(CIF,Nama_nasabah,Alamat )"
                    +"VALUES (?,?,?);";
            
            pStatement = conn.prepareStatement(sql);
            
            //Input Kode MK
            System.out.print("Masukan CIF          : ");
            String CIF = userInput.readLine();
            pStatement.setString(1, CIF);
            
            //Input Nama MK
            System.out.print("Masukan Nama Nasabah: ");
            String Nama_nasabah = userInput.readLine();
            pStatement.setString(2, Nama_nasabah);
            
            //Input Nama MK
            System.out.print("Masukan Alamat        : ");
            String Alamat = userInput.readLine();
            pStatement.setString(3, Alamat);
            
            //Cek Data berhasil ditambah atau gagal
            int intBaris=pStatement.executeUpdate();
            if (intBaris>0){
                System.out.println("Berhasil menambahkan data");
            } else {
                System.out.println("Penambahan data gagal");
            }
            
            //Close koneksi
            pStatement.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver tidak ditemukan");
        } catch (IOException ex) {
            Logger.getLogger(ProgramPinjamanBank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void insert_karyawan()
    {
        BufferedReader userInput = new BufferedReader (new InputStreamReader(System.in));
        
        //Cek Error dengan try
        try{
            //Koneksi Database
            Class.forName("com.mysql.jdbc.Driver");
            urlValue="jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
            Connection conn = DriverManager.getConnection(urlValue);
            
            PreparedStatement pStatement = null;
            //Input Data
            String sql = "INSERT INTO karyawan(NIP,Nama_karyawan,Alamat )"
                    +"VALUES (?,?,?);";
            
            pStatement = conn.prepareStatement(sql);
            
            //Input Kode MK
            System.out.print("Masukan NIP          : ");
            String NIP = userInput.readLine();
            pStatement.setString(1, NIP);
            
            //Input Nama MK
            System.out.print("Masukan Nama Karyawan: ");
            String Nama_karyawan = userInput.readLine();
            pStatement.setString(2, Nama_karyawan);
            
            //Input Nama MK
            System.out.print("Masukan Alamat        : ");
            String Alamat = userInput.readLine();
            pStatement.setString(3, Alamat);
            
            //Cek Data berhasil ditambah atau gagal
            int intBaris=pStatement.executeUpdate();
            if (intBaris>0){
                System.out.println("Berhasil menambahkan data");
            } else {
                System.out.println("Penambahan data gagal");
            }
            
            //Close koneksi
            pStatement.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver tidak ditemukan");
        } catch (IOException ex) {
            Logger.getLogger(ProgramPinjamanBank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void insert_jaminan()
    {
        //Menambah Objek IO
        BufferedReader userInput = new BufferedReader (new InputStreamReader(System.in));
        
        //Cek Error dengan try
        try
        {
            //Koneksi Database
            Class.forName("com.mysql.jdbc.Driver");
            urlValue="jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
            Connection conn = DriverManager.getConnection(urlValue);
            
            PreparedStatement pStatement = null;
            //Input Data
            String sql = "INSERT INTO jaminan(Kode_properti,Nama_properti,Harga_properti )"
                    +"VALUES (?,?,?);";
            
            pStatement = conn.prepareStatement(sql);
            
            //Input Kode MK
            System.out.print("Masukan Kode Properti: ");
            String Kode_properti = userInput.readLine();
            pStatement.setString(1, Kode_properti);
            
            //Input Nama MK
            System.out.print("Masukan Nama Properti: ");
            String Nama_properti = userInput.readLine();
            pStatement.setString(2, Nama_properti);
            
            //Input Nama MK
            System.out.print("Masukan Harga Properti: ");
            String Harga_properti = userInput.readLine();
            pStatement.setString(3, Harga_properti);
            
            //Cek Data berhasil ditambah atau gagal
            int intBaris=pStatement.executeUpdate();
            if (intBaris>0){
                System.out.println("Berhasil menambahkan data");
            } else {
                System.out.println("Penambahan data gagal");
            }
            
            //Close koneksi
            pStatement.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver tidak ditemukan");
        } catch (IOException ex) {
            Logger.getLogger(ProgramPinjamanBank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void kembali() 
    {
        utama();
    }
    
    void tampil() 
    {
        System.out.println("“=====Tampil=====”");
        System.out.println(" 1.  PINJAMAN     ");
        System.out.println(" 2.  NASABAH      ");
        System.out.println(" 3.  KARYAWAN     ");
        System.out.println(" 4.  JAMINAN      ");
        System.out.println(" 5.  KEMBALI      ");
        System.out.println("");
        System.out.print("MASUKAN PILIHAN : ");
        int pilihan = this.masuk.nextInt();

        switch (pilihan) {
        case 1: tampil_pinjaman();
        break;
        case 2: tampil_nasabah();
        break;
        case 3: tampil_karyawan();
        break;
        case 4: tampil_jaminan();
        break;
        case 5: kembali();
        break;
        default : System.out.println("“pilihan yang kamu masukkan salah”");
        tampil();
        }
    }
    void tampil_pinjaman() 
    {
    System.out.println("Tinggal isi");
    }
    void tampil_nasabah() 
    {
    System.out.println("Tinggal isi ");
    }
    void tampil_karyawan()
    {
    System.out.println("Tinggal isi ");
    }
    void tampil_jaminan()
    {
        
    }
    
    void edit()
    {
        System.out.println("“======Edit======”");
        System.out.println(" 1.  PINJAMAN     ");
        System.out.println(" 2.  NASABAH      ");
        System.out.println(" 3.  KARYAWAN     ");
        System.out.println(" 4.  JAMINAN      ");
        System.out.println(" 5.  KEMBALI      ");
        System.out.println("");
        System.out.print("MASUKAN PILIHAN : ");
        int pilihan = this.masuk.nextInt(); 
        
        switch (pilihan) {
        case 1: edit_pinjaman();
        break;
        case 2: edit_nasabah();
        break;
        case 3: edit_karyawan();
        break;
        case 4: edit_jaminan();
        break;
        case 5: kembali();
        break;
        default : System.out.println("“pilihan yang kamu masukkan salah”");
        edit();
        }
    }
    void edit_pinjaman() 
    {
        Scanner isi = new Scanner(System.in);
        String cari_rekening = null;
        int no = 0;
        int pilihan_edit;
        BufferedReader userInput = new BufferedReader (new InputStreamReader(System.in));
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            urlValue="jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
            Connection conn = DriverManager.getConnection(urlValue);
            Statement st = conn.createStatement();
            
            System.out.println("Masukan Rekening yang ingin di edit : ");
            cari_rekening = isi.nextLine();
            ResultSet rs = st.executeQuery("SELECT * FROM pinjaman WHERE rekening = "+cari_rekening+" ;");

            while(rs.next())
            {
                no = no+1;
                System.out.println("Rekening "+rs.getString("Rekening")+" Tedaftar");
            }
            
            if(no==0)
            {
                System.out.println("Rekening "+cari_rekening+" Tidak ditemukan");
            }
            else
            {
                System.out.println("====== Edit ======");
                System.out.println(" 1. Saldo         ");
                System.out.println(" 2. Limit Kredit  ");
                System.out.println(" 3. Jangka Waktu  ");
                System.out.println(" 4. Total Angsuran");
                System.out.println("==================");
                System.out.print("Pilihan : ");
                pilihan_edit = isi.nextInt();
     
                if(pilihan_edit==1)
                {
                    PreparedStatement pStatement = null;
                    String sql = "UPDATE pinjaman SET Saldo = ? WHERE rekening = '"+cari_rekening+"' ;";
                    pStatement = conn.prepareStatement(sql);

                    System.out.print("Masukan Saldo: ");
                    String ganti_saldo = userInput.readLine();
                    pStatement.setString(1, ganti_saldo);
                    
                    int intBaris=pStatement.executeUpdate();
                    if (intBaris>0)
                    {
                        System.out.println("Berhasil menambahkan data");
                    } 
                    else   
                    {
                        System.out.println("Penambahan data gagal");
                    }
                }
                else
                {
                    if(pilihan_edit==2)
                    {
                        PreparedStatement pStatement = null;
                        String sql = "UPDATE Pinjaman SET Limit_kredit = ? WHERE Rekening = '"+cari_rekening+"' ;";
                        pStatement = conn.prepareStatement(sql);

                        System.out.print("Masukan Limit Kredit: ");
                        String ganti_limit = userInput.readLine();
                        pStatement.setString(1, ganti_limit);

                        int intBaris=pStatement.executeUpdate();
                        if (intBaris>0)
                        {
                            System.out.println("Berhasil mengubah data");
                        } 
                        else   
                        {
                            System.out.println("Mengubah data gagal");
                        }  
                    }
                    else
                    {
                        if(pilihan_edit==3)
                        {
                            PreparedStatement pStatement = null;
                            String sql = "UPDATE Pinjaman SET Jangka_waktu = ? "
                                    + "WHERE Rekening = '"+cari_rekening+"' ;";
                            pStatement = conn.prepareStatement(sql);
                            
                            System.out.print("Masukan Jangka Waktu: ");
                            String ganti_waktu = userInput.readLine();
                            pStatement.setString(1, ganti_waktu);
                            
                            int intBaris=pStatement.executeUpdate();
                            if (intBaris>0)
                            {
                                System.out.println("Berhasil mengubah data");
                            } 
                            else   
                            {
                                System.out.println("Mengubah data gagal");
                            }
                        }
                        else
                        {
                            if(pilihan_edit==4)
                            {
                                PreparedStatement pStatement = null;
                                String sql = "UPDATE Pinjaman SET Jangka_waktu = ? "
                                        + "WHERE Rekening = '"+cari_rekening+"' ;";
                                pStatement = conn.prepareStatement(sql);

                                System.out.print("Masukan Jangka Waktu: ");
                                String ganti_waktu = userInput.readLine();
                                pStatement.setString(1, ganti_waktu);
                                
                                int intBaris=pStatement.executeUpdate();
                                if (intBaris>0)
                                {
                                    System.out.println("Berhasil mengubah data");
                                } 
                                else   
                                {
                                    System.out.println("Mengubah data gagal");
                                }
                            }                            
                        }
                    }
                    
                }
            }
            st.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver tidak ditemukan");
        } catch(IOException ex){
            Logger.getLogger(ProgramPinjamanBank.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print("Kembali Ke menu utama? (Y/N)");
        String tahan = isi.nextLine();
        if("Y".equals(tahan))
        {
            utama();
        }
        else
        {
            System.exit(0);
        }
    }
    void edit_nasabah() 
    {
        Scanner isi = new Scanner(System.in);
        String cari_cif = null;
        int no = 0;
        int pilihan_edit;
        BufferedReader userInput = new BufferedReader (new InputStreamReader(System.in));
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            urlValue="jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
            Connection conn = DriverManager.getConnection(urlValue);
            Statement st = conn.createStatement();
            
            System.out.println("Masukan CIF yang ingin di edit : ");
            cari_cif = isi.nextLine();
            ResultSet rs = st.executeQuery("SELECT * FROM nasabah WHERE CIF = "+cari_cif+" ;");

            while(rs.next())
            {
                no = no+1;
                System.out.println("CIF "+rs.getString("CIF")+" Tedaftar");
            }
            
            if(no==0)
            {
                System.out.println("CIF "+cari_cif+" Tidak ditemukan");
            }
            else
            {
                System.out.println("==== Edit =====");
                System.out.println(" 1. Nama       ");
                System.out.println(" 2. Alamat     ");
                System.out.println("===============");
                System.out.print("Pilihan : ");
                pilihan_edit = isi.nextInt();
     
                if(pilihan_edit==1)
                {
                    PreparedStatement pStatement = null;
                    String sql = "UPDATE nasabah SET Nama_Nasabah = ? WHERE CIF = '"+cari_cif+"' ;";
                    pStatement = conn.prepareStatement(sql);

                    System.out.print("Masukan Nama: ");
                    String ganti_nama = userInput.readLine();
                    pStatement.setString(1, ganti_nama);
                    
                    int intBaris=pStatement.executeUpdate();
                    if (intBaris>0)
                    {
                        System.out.println("Berhasil menambahkan data");
                    } 
                    else   
                    {
                        System.out.println("Penambahan data gagal");
                    }
                }
                else
                {
                    if(pilihan_edit==2);
                    {
                        PreparedStatement pStatement = null;
                        String sql = "UPDATE nasabah SET Alamat = ? WHERE CIF = '"+cari_cif+"' ;";
                        pStatement = conn.prepareStatement(sql);

                        System.out.print("Masukan Alamat: ");
                        String ganti_alamat = userInput.readLine();
                        pStatement.setString(1, ganti_alamat);

                        int intBaris=pStatement.executeUpdate();
                        if (intBaris>0)
                        {
                            System.out.println("Berhasil mengubah data");
                        } 
                        else   
                        {
                            System.out.println("Mengubah data gagal");
                        }  
                    }
                }
            }
            st.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver tidak ditemukan");
        } catch(IOException ex){
            Logger.getLogger(ProgramPinjamanBank.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print("Kembali Ke menu utama? (Y/N)");
        String tahan = isi.nextLine();
        if("Y".equals(tahan))
        {
            utama();
        }
        else
        {
            System.exit(0);
        }        
    }
    void edit_karyawan()
    {
        Scanner isi = new Scanner(System.in);
        String cari_nip = null;
        int no = 0;
        int pilihan_edit;
        BufferedReader userInput = new BufferedReader (new InputStreamReader(System.in));
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            urlValue="jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
            Connection conn = DriverManager.getConnection(urlValue);
            Statement st = conn.createStatement();
            
            System.out.println("Masukan NIP yang ingin di edit : ");
            cari_nip = isi.nextLine();
            ResultSet rs = st.executeQuery("SELECT * FROM karyawan WHERE NIP = "+cari_nip+" ;");

            while(rs.next())
            {
                no = no+1;
                System.out.println("NIP "+rs.getString("NIP")+" Tedaftar");
            }
            
            if(no==0)
            {
                System.out.println("NIP "+cari_nip+" Tidak ditemukan");
            }
            else
            {
                System.out.println("==== Edit =====");
                System.out.println(" 1. Nama       ");
                System.out.println(" 2. Alamat     ");
                System.out.println("===============");
                System.out.print("Pilihan : ");
                pilihan_edit = isi.nextInt();
     
                if(pilihan_edit==1)
                {
                    PreparedStatement pStatement = null;
                    String sql = "UPDATE karyawan SET Nama_Karyawan = ? WHERE NIP = '"+cari_nip+"' ;";
                    pStatement = conn.prepareStatement(sql);

                    System.out.print("Masukan Nama: ");
                    String ganti_nama = userInput.readLine();
                    pStatement.setString(1, ganti_nama);
                    
                    int intBaris=pStatement.executeUpdate();
                    if (intBaris>0)
                    {
                        System.out.println("Berhasil menambahkan data");
                    } 
                    else   
                    {
                        System.out.println("Penambahan data gagal");
                    }
                }
                else
                {
                    if(pilihan_edit==2);
                    {
                        PreparedStatement pStatement = null;
                        String sql = "UPDATE karyawan SET Alamat = ? WHERE NIP = '"+cari_nip+"' ;";
                        pStatement = conn.prepareStatement(sql);

                        System.out.print("Masukan Alamat: ");
                        String ganti_alamat = userInput.readLine();
                        pStatement.setString(1, ganti_alamat);

                        int intBaris=pStatement.executeUpdate();
                        if (intBaris>0)
                        {
                            System.out.println("Berhasil mengubah data");
                        } 
                        else   
                        {
                            System.out.println("Mengubah data gagal");
                        }  
                    }
                }
            }
            st.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver tidak ditemukan");
        } catch(IOException ex){
            Logger.getLogger(ProgramPinjamanBank.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print("Kembali Ke menu utama? (Y/N)");
        String tahan = isi.nextLine();
        if("Y".equals(tahan))
        {
            utama();
        }
        else
        {
            System.exit(0);
        }        
    }
    void edit_jaminan()
    {
        Scanner isi = new Scanner(System.in);
        String cari_kode_properti = null;
        int no = 0;
        int pilihan_edit;
        BufferedReader userInput = new BufferedReader (new InputStreamReader(System.in));
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            urlValue="jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
            Connection conn = DriverManager.getConnection(urlValue);
            Statement st = conn.createStatement();
            
            System.out.println("Masukan Kode Properti yang ingin di edit : ");
            cari_kode_properti = isi.nextLine();
            ResultSet rs = st.executeQuery("SELECT * FROM jaminan WHERE kode_properti = "+cari_kode_properti+" ;");

            while(rs.next())
            {
                no = no+1;
                System.out.println("Kode Properti "+rs.getString("kode_properti")+" Tedaftar");
            }
            
            if(no==0)
            {
                System.out.println("Kode Properti "+cari_kode_properti+" Tidak ditemukan");
            }
            else
            {
                System.out.println("====== Edit ======");
                System.out.println(" 1. Nama Properti ");
                System.out.println(" 2. Harga Properti");
                System.out.println("==================");
                System.out.print("Pilihan : ");
                pilihan_edit = isi.nextInt();
     
                if(pilihan_edit==1)
                {
                    PreparedStatement pStatement = null;
                    String sql = "UPDATE jaminan SET Nama_properti = ? WHERE kode_properti = '"+cari_kode_properti+"' ;";
                    pStatement = conn.prepareStatement(sql);

                    System.out.print("Masukan Nama Properti: ");
                    String ganti_nama = userInput.readLine();
                    pStatement.setString(1, ganti_nama);
                    
                    int intBaris=pStatement.executeUpdate();
                    if (intBaris>0)
                    {
                        System.out.println("Berhasil menambahkan data");
                    } 
                    else   
                    {
                        System.out.println("Penambahan data gagal");
                    }
                }
                else
                {
                    if(pilihan_edit==2);
                    {
                        PreparedStatement pStatement = null;
                        String sql = "UPDATE jaminan SET harga_properti = ? WHERE kode_properti = '"+cari_kode_properti+"' ;";
                        pStatement = conn.prepareStatement(sql);

                        System.out.print("Masukan Harga Properti: ");
                        String ganti_harga = userInput.readLine();
                        pStatement.setString(1, ganti_harga);

                        int intBaris=pStatement.executeUpdate();
                        if (intBaris>0)
                        {
                            System.out.println("Berhasil mengubah data");
                        } 
                        else   
                        {
                            System.out.println("Mengubah data gagal");
                        }  
                    }
                }
            }
            st.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver tidak ditemukan");
        } catch(IOException ex){
            Logger.getLogger(ProgramPinjamanBank.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print("Kembali Ke menu utama? (Y/N)");
        String tahan = isi.nextLine();
        if(tahan.equals("Y"))
        {
            utama();
        }
        else
        {
            System.exit(0);
        }
    }
    
    void searching()
    {
        System.out.println("“======Cari======”");
        System.out.println(" 1.  PINJAMAN     ");
        System.out.println(" 2.  NASABAH      ");
        System.out.println(" 3.  KARYAWAN     ");
        System.out.println(" 4.  JAMINAN      ");
        System.out.println(" 5.  KEMBALI      ");
        System.out.println("");
        System.out.print("MASUKAN PILIHAN : ");
        int pilihan = this.masuk.nextInt(); 

        switch (pilihan) 
        {
        case 1: cari_pinjaman();
        break;
        case 2: cari_nasabah();
        break;
        case 3: cari_karyawan();
        break;
        case 4: cari_jaminan();
        break;
        case 5: kembali();
        break;
        default : System.out.println("“pilihan yang kamu masukkan salah”");
        searching();
        }
    }
    void cari_pinjaman() 
    {
        int cari_rekening = 0;
        int menu_saldo = 0;
        int cari_saldo = 0;
        int error_check=0;
        int menu_pilihan = 0;
        int no;
          
        BufferedReader userInput = new BufferedReader (new InputStreamReader(System.in));
        Scanner menu = new Scanner(System.in);
        Scanner isi = new Scanner(System.in);
        
        //Cek Error dengan try
        try{
            //Koneksi Database
            Class.forName("com.mysql.jdbc.Driver");
            urlValue="jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
            Connection conn = DriverManager.getConnection(urlValue);
            Statement st = conn.createStatement();
            
            do{
                error_check=0;
                System.out.println("======== Menu Pilihan ========");
                System.out.println(" 1. Berdasarkan Rekening");
                System.out.println(" 2. Berdasarkan Saldo");
                System.out.println("==============================");
                System.out.print("Pilihan : ");                              
                menu_pilihan = menu.nextInt();
 
                if(menu_pilihan==1)
                {
                    System.out.print("Masukan Kode Properti Yang Ingin Dicari: ");
                    cari_rekening = isi.nextInt();
                    ResultSet rs = st.executeQuery("SELECT * FROM Pinjaman WHERE "
                            + "rekening = "+cari_rekening+" ;");         
  
                    no=0;
                    while(rs.next())
                    {
                        no = no+1;
                        System.out.println("Rekening : "+rs.getString("Rekening"));
                        System.out.println("Saldo : "+rs.getString("Saldo"));
                        System.out.println("Kode Properti : "+rs.getString("Kode_properti"));
                        System.out.println("Limit Kredit : "+rs.getString("Limit_kredit"));
                        System.out.println("Jangka Waktu : "+rs.getString("jangka_waktu"));
                        System.out.println("Total Angsuran : "+rs.getString("total_angsuran"));
                        System.out.println("CIF : "+rs.getString("CIF"));
                        System.out.println("NIP : "+rs.getString("NIP"));
                        System.out.println("\n");
                    }
            
                    if(no==0)
                    {
                        System.out.println("Rekening "+cari_rekening+" Tidak ditemukan");
                        error_check=1;
                    }
                }
                else
                {
                    if(menu_pilihan==2)
                    {
                        System.out.println("====== Berdasarkan Harga ======");
                        System.out.println(" 1. Lebih Besar Dari");
                        System.out.println(" 2. Lebih Kecil Dari");
                        System.out.println("===============================");
                        System.out.print("Pilihan? ");
                        menu_saldo = menu.nextInt();  
                        
                        if(menu_saldo==1)
                        {
                            System.out.print("Masukan Saldo Yang Ingin Dicari: ");
                            cari_saldo = isi.nextInt();
                            ResultSet rs = st.executeQuery("SELECT * FROM Pinjaman "
                                    + "WHERE Saldo > "
                                    +cari_saldo+" ;");         

                            no=0;
                            while(rs.next())
                            {
                                no = no+1;
                                System.out.println("Rekening : "+rs.getString("Rekening"));
                                System.out.println("Saldo : "+rs.getString("Saldo"));
                                System.out.println("Kode Properti : "+rs.getString("Kode_properti"));
                                System.out.println("Limit Kredit : "+rs.getString("Limit_kredit"));
                                System.out.println("Jangka Waktu : "+rs.getString("jangka_waktu"));
                                System.out.println("Total Angsuran : "+rs.getString("total_angsuran"));
                                System.out.println("CIF : "+rs.getString("CIF"));
                                System.out.println("NIP : "+rs.getString("NIP"));
                                System.out.println("\n");
                            }

                            if(no==0)
                            {
                                System.out.println("Nama "+cari_saldo+" Tidak ditemukan");
                                error_check=1;
                            }
                        }
                        else
                        {
                            if(menu_saldo==2)
                            {
                                System.out.print("Masukan Saldo Yang Ingin Dicari: ");
                                cari_saldo = isi.nextInt();
                                ResultSet rs = st.executeQuery("SELECT * FROM Pinjaman "
                                    + "WHERE Saldo < "+cari_saldo+" ;");         

                                no=0;
                                while(rs.next())
                                {
                                    no = no+1;
                                    System.out.println("Rekening : "+rs.getString("Rekening"));
                                    System.out.println("Saldo : "+rs.getString("Saldo"));
                                    System.out.println("Kode Properti : "+rs.getString("Kode_properti"));
                                    System.out.println("Limit Kredit : "+rs.getString("Limit_kredit"));
                                    System.out.println("Jangka Waktu : "+rs.getString("jangka_waktu"));
                                    System.out.println("Total Angsuran : "+rs.getString("total_angsuran"));
                                    System.out.println("CIF : "+rs.getString("CIF"));
                                    System.out.println("NIP : "+rs.getString("NIP"));
                                    System.out.println("\n");
                                }

                                if(no==0)
                                {
                                    System.out.println("Nama "+cari_saldo+" Tidak ditemukan");
                                    error_check=1;
                                }                        
                            }
                        }
                    }
                    else
                    {
                        System.out.println("Tidak Ada Menu Pilihan Tersebut Ulangi!");
                        error_check=1;
                    }
                }
            }while(error_check==1);
            //Close koneksi
            st.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver tidak ditemukan");
        } 
        System.out.print("Kembali Ke menu utama? (Y/N)");
        String tahan = isi.nextLine();
        if("Y".equals(tahan))
        {
            utama();
        }
        else
        {
            System.exit(0);
        }
    }
    void cari_nasabah() 
    {   
        String cari_cif = null;
        String cari_nama_nasabah= null;
        String cari_alamat_nasabah = null;
        int error_check=0;
        int menu_pilihan = 0;
        int no;
        String tahan = null;
          
        Scanner menu = new Scanner(System.in);
        Scanner isi = new Scanner(System.in);        
        try{

            Class.forName("com.mysql.jdbc.Driver");
            urlValue="jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
            Connection conn = DriverManager.getConnection(urlValue);
            Statement st = conn.createStatement();
            
            do{
                error_check=0;
                System.out.println("==== Menu Pilihan ====");
                System.out.println(" 1. Berdasarkan CIF");
                System.out.println(" 2. Berdasarkan Nama");
                System.out.println(" 3. Berdasarkan Alamat");
                System.out.println("======================");
                System.out.print("Pilihan : ");                              
                menu_pilihan = menu.nextInt();
               
 
                if(menu_pilihan==1)
                {

                    System.out.print("Masukan CIF Yang Ingin Dicari: ");
                    cari_cif = isi.nextLine();
                    ResultSet rs = st.executeQuery("SELECT * FROM nasabah WHERE CIF = "+cari_cif+" ;");         
  
                    no=0;
                    while(rs.next())
                    {
                        no = no+1;
                        System.out.println("CIF : "+rs.getString("CIF"));
                        System.out.println("Nama Nasabah : "+rs.getString("Nama_Nasabah"));
                        System.out.println("Alamat : "+rs.getString("Alamat"));
                        System.out.println("\n");
                    }
            
                    if(no==0)
                    {
                        System.out.println("CIF "+cari_cif+" Tidak ditemukan");
                        error_check=1;
                    }
                }
                else
                {
                    if(menu_pilihan==2)
                    {
                        System.out.print("Masukan Nama Yang Ingin Dicari: ");
                        cari_nama_nasabah = isi.nextLine();
                        ResultSet rs = st.executeQuery("SELECT * FROM nasabah "
                                + "WHERE Nama_Nasabah LIKE "
                                +"'%"+cari_nama_nasabah+"%'"+";");         

                        no=0;
                        while(rs.next())
                        {
                            no = no+1;
                            System.out.println("CIF : "+rs.getString("CIF"));
                            System.out.println("Nama Nasabah : "+rs.getString("Nama_Nasabah"));
                            System.out.println("Alamat : "+rs.getString("Alamat"));
                            System.out.println("\n");
                        }

                        if(no==0)
                        {
                            System.out.println("Nama "+cari_nama_nasabah+" Tidak ditemukan");
                            error_check=1;
                        }   
                    }
                    else
                    {
                        if(menu_pilihan==3)
                        {
                            System.out.print("Masukan Alamat Yang Ingin Dicari: ");
                            cari_alamat_nasabah = isi.nextLine();
                            ResultSet rs = st.executeQuery("SELECT * FROM nasabah "
                                    + "WHERE Alamat LIKE "+"'"+cari_alamat_nasabah+"'"+";");         

                            no=0;
                            while(rs.next())
                            {
                                no = no+1;
                                System.out.println("CIF : "+rs.getString("CIF"));
                                System.out.println("Nama Nasabah : "+rs.getString("Nama_Nasabah"));
                                System.out.println("Alamat : "+rs.getString("Alamat"));
                                System.out.println("\n");
                            }

                            if(no==0)
                            {
                                System.out.println("Alamat "+cari_alamat_nasabah+" Tidak ditemukan");
                                error_check=1;
                            }
                        }
                        else
                        {
                            System.out.println("Tidak Ada Menu Pilihan Tersebut Ulangi!");
                            error_check=1;
                        }
                    }
                }
            }while(error_check==1);
            st.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver tidak ditemukan");
        }
        System.out.print("Kembali Ke menu utama? (Y/N)");
        tahan = isi.nextLine();
        if("Y".equals(tahan))
        {
            utama();
        }
        else
        {
            System.exit(0);
        }
    }
    void cari_karyawan()
    {
        String cari_nip = null;
        String cari_nama_karyawan = null;
        String cari_alamat_karyawan = null;
        String tahan = null;
        int error_check=0;
        int menu_pilihan = 0;
        int no;
          
        Scanner menu = new Scanner(System.in);
        Scanner isi = new Scanner(System.in);
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            urlValue="jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
            Connection conn = DriverManager.getConnection(urlValue);
            Statement st = conn.createStatement();
            
            do{
                error_check=0;
                System.out.println("==== Menu Pilihan ====");
                System.out.println(" 1. Berdasarkan NIP");
                System.out.println(" 2. Berdasarkan Nama");
                System.out.println(" 3. Berdasarkan Alamat");
                System.out.println("======================");
                System.out.print("Pilihan : ");                              
                menu_pilihan = menu.nextInt();
 
                if(menu_pilihan==1)
                {
                    System.out.print("Masukan NIP Yang Ingin Dicari: ");
                    cari_nip = isi.nextLine();
                    ResultSet rs = st.executeQuery("SELECT * FROM Karyawan WHERE "
                            + "NIP = "+cari_nip+" ;");         
  
                    no=0;
                    while(rs.next())
                    {
                        no = no+1;
                        System.out.println("NIP : "+rs.getString("NIP"));
                        System.out.println("Nama Karyawan : "+rs.getString("Nama_Karyawan"));
                        System.out.println("Alamat : "+rs.getString("Alamat"));
                        System.out.println("\n");
                    }
            
                    if(no==0)
                    {
                        System.out.println("NIP "+cari_nip+" Tidak ditemukan");
                        error_check=1;
                    }
                }
                else
                {
                    if(menu_pilihan==2)
                    {
                        System.out.print("Masukan Nama Yang Ingin Dicari: ");
                        cari_nama_karyawan = isi.nextLine();
                        ResultSet rs = st.executeQuery("SELECT * FROM karyawan "
                                + "WHERE Nama_Karyawan LIKE "
                                +"'%"+cari_nama_karyawan+"%'"+";");         

                        no=0;
                        while(rs.next())
                        {
                            no = no+1;
                            System.out.println("NIP : "+rs.getString("NIP"));
                            System.out.println("Nama Karyawan : "+rs.getString("Nama_Karyawan"));
                            System.out.println("Alamat : "+rs.getString("Alamat"));
                            System.out.println("\n");
                        }

                        if(no==0)
                        {
                            System.out.println("Nama "+cari_nama_karyawan+" Tidak ditemukan");
                            error_check=1;
                        }   
                    }
                    else
                    {
                        if(menu_pilihan==3)
                        {
                            System.out.print("Masukan Alamat Yang Ingin Dicari: ");
                            cari_alamat_karyawan = isi.nextLine();
                            ResultSet rs = st.executeQuery("SELECT * FROM karyawan "
                                    + "WHERE Alamat LIKE "+"'"+cari_alamat_karyawan+"'"+";");         

                            no=0;
                            while(rs.next())
                            {
                                no = no+1;
                                System.out.println("NIP : "+rs.getString("NIP"));
                                System.out.println("Nama Karyawan : "+rs.getString("Nama_Karyawan"));
                                System.out.println("Alamat : "+rs.getString("Alamat"));
                                System.out.println("\n");
                            }

                            if(no==0)
                            {
                                System.out.println("Alamat "+cari_alamat_karyawan+" Tidak ditemukan");
                                error_check=1;
                            }
                        }
                        else
                        {
                            System.out.println("Tidak Ada Menu Pilihan Tersebut Ulangi!");
                            error_check=1;
                        }
                    }
                }
            }while(error_check==1);

            st.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver tidak ditemukan");
        }
        
        System.out.print("Kembali Ke menu utama? (Y/N)");
        tahan = isi.nextLine();
        if("Y".equals(tahan))
        {
            utama();
        }
        else
        {
            System.exit(0);
        }
    }
    void cari_jaminan()
    {
        String cari_kode_properti = null;
        String cari_nama_properti = null;
        String tahan = null;
        int cari_harga_properti = 0;
        int error_check=0;
        int menu_pilihan = 0;
        int no;
          
        Scanner menu = new Scanner(System.in);
        Scanner isi = new Scanner(System.in);
        
        //Cek Error dengan try
        try{
            //Koneksi Database
            Class.forName("com.mysql.jdbc.Driver");
            urlValue="jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
            Connection conn = DriverManager.getConnection(urlValue);
            Statement st = conn.createStatement();
            
            do{
                error_check=0;
                System.out.println("======== Menu Pilihan ========");
                System.out.println(" 1. Berdasarkan Kode Properti");
                System.out.println(" 2. Berdasarkan Nama Properti");
                System.out.println(" 3. Berdasarkan Harga Properti");
                System.out.println("==============================");
                System.out.print("Pilihan : ");                              
                menu_pilihan = menu.nextInt();
 
                if(menu_pilihan==1)
                {
                    System.out.print("Masukan Kode Properti Yang Ingin Dicari: ");
                    cari_kode_properti = isi.nextLine();
                    ResultSet rs = st.executeQuery("SELECT * FROM Jaminan WHERE "
                            + "Kode_properti = "+cari_kode_properti+" ;");         
  
                    no=0;
                    while(rs.next())
                    {
                        no = no+1;
                        System.out.println("Kode Properti : "+rs.getString("Kode_properti"));
                        System.out.println("Nama Properti : "+rs.getString("Nama_properti"));
                        System.out.println("Harga Properti : "+rs.getString("harga_properti"));
                        System.out.println("\n");
                    }
            
                    if(no==0)
                    {
                        System.out.println("CIF "+cari_kode_properti+" Tidak ditemukan");
                        error_check=1;
                    }
                }
                else
                {
                    if(menu_pilihan==2)
                    {
                        System.out.print("Masukan Nama Properti Yang Ingin Dicari: ");
                        cari_nama_properti = isi.nextLine();
                        ResultSet rs = st.executeQuery("SELECT * FROM Jaminan "
                                + "WHERE Nama_properti LIKE "
                                +"'%"+cari_nama_properti+"%'"+";");         

                        no=0;
                        while(rs.next())
                        {
                            no = no+1;
                            System.out.println("Kode Properti : "+rs.getString("Kode_properti"));
                            System.out.println("Nama Properti : "+rs.getString("Nama_properti"));
                            System.out.println("Harga Properti : "+rs.getString("harga_properti"));
                            System.out.println("\n");
                        }

                        if(no==0)
                        {
                            System.out.println("Nama "+cari_nama_properti+" Tidak ditemukan");
                            error_check=1;
                        }   
                    }
                    else
                    {
                        if(menu_pilihan==3)
                        {
                            System.out.println("====== Berdasarkan Harga ======");
                            System.out.println(" 1. Lebih Besar Dari");
                            System.out.println(" 2. Lebih Kecil Dari");
                            System.out.println("===============================");
                            System.out.print("Pilihan? ");
                            int pilihan_harga = menu.nextInt();
                            
                            if(pilihan_harga==1)
                            {
                                System.out.print("Masukan Harga Properti Yang Ingin Dicari: ");
                                cari_harga_properti = isi.nextInt();
                                ResultSet rs = st.executeQuery("SELECT * FROM jaminan "
                                        + "WHERE harga_properti > "+cari_harga_properti+";");         

                                no=0;
                                while(rs.next())
                                {
                                    no = no+1;
                                    System.out.println("Kode Properti : "+rs.getString("Kode_properti"));
                                    System.out.println("Nama Properti : "+rs.getString("Nama_properti"));
                                    System.out.println("Harga Properti : "+rs.getString("harga_properti"));
                                    System.out.println("\n");
                                }

                                if(no==0)
                                {
                                    System.out.println("Alamat "+cari_harga_properti+" Tidak ditemukan");
                                    error_check=1;
                                }
                            }
                            else
                            {
                                if(pilihan_harga==2)
                                {
                                    System.out.print("Masukan Harga Properti Yang Ingin Dicari: ");
                                    cari_harga_properti = isi.nextInt();
                                    ResultSet rs = st.executeQuery("SELECT * FROM jaminan "
                                            + "WHERE harga_properti < "+cari_harga_properti+";");         

                                    no=0;
                                    while(rs.next())
                                    {
                                        no = no+1;
                                        System.out.println("Kode Properti : "+rs.getString("Kode_properti"));
                                        System.out.println("Nama Properti : "+rs.getString("Nama_properti"));
                                        System.out.println("Harga Properti : "+rs.getString("harga_properti"));
                                        System.out.println("\n");
                                    }

                                    if(no==0)
                                    {
                                        System.out.println("Alamat "+cari_harga_properti+" Tidak ditemukan");
                                        error_check=1;
                                    }
                                }
                                else
                                {
                                    System.out.println("Tidak Ada Menu Pilihan Tersebut Ulangi!");
                                    error_check=1;
                                }
                            }
                        }
                        else
                        {
                            System.out.println("Tidak Ada Menu Pilihan Tersebut Ulangi!");
                            error_check=1;
                        }
                    }
                }
            }while(error_check==1);

            st.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver tidak ditemukan");
        }  
        System.out.print("Kembali Ke menu utama? (Y/N)");
        tahan = isi.nextLine();
        if("Y".equals(tahan))
        {
            utama();
        }
        else
        {
            System.exit(0);
        }
    }
    
    void delete()
    {
       System.out.println("“======Delete======”");
        System.out.println(" 1.  DATA PINJAMAN     ");
        System.out.println(" 2.  DATA NASABAH      ");
        System.out.println(" 3.  DATA KARYAWAN     ");
        System.out.println(" 4.  DATA JAMINAN      ");
        System.out.println(" 5.  KEMBALI           ");
        System.out.println("");
        System.out.print("MASUKAN PILIHAN : ");
        int pilihan = this.masuk.nextInt(); 
        
        switch (pilihan) {
        case 1: hps_pinjaman();
        break;
        case 2: hps_nasabah();
        break;
        case 3: hps_karyawan();
        break;
        case 4: hps_jaminan();
        break;
        case 5: kembali();
        break;
        default : System.out.println("“pilihan yang kamu masukkan salah”");
        edit();
        }
    }
    void hps_pinjaman ()
    {
         Scanner isi = new Scanner(System.in);
        String cari_rek = null;
        int no = 0;
        int pilihan_edit;
        BufferedReader userInput = new BufferedReader (new InputStreamReader(System.in));
        
        try
        {
          Class.forName("com.mysql.jdbc.Driver");
            urlValue="jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
            Connection conn = DriverManager.getConnection(urlValue);
            Statement st = conn.createStatement();
            
            System.out.println("Masukan Rekening yang ingin di hapus : ");
            cari_rek = isi.nextLine();
            ResultSet rs = st.executeQuery("UPDATE pinjaman SET  pinjaman.status_P = 0 WHERE  pinjaman.rekening="+cari_rek+"' ;");
             while(rs.next())
            {
                no = no+1;
                System.out.println("Rekening "+rs.getString("Rekening")+" Terhapus");
                
            }
            
            if(no==0)
            {
                System.out.println("CIF "+cari_rek+" Tidak ditemukan");
            }
          
              st.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver tidak ditemukan");
            
            
        }
    }
    void hps_nasabah()
    {
          Scanner isi = new Scanner(System.in);
        String cari_cif = null;
        int no = 0;
        int pilihan_edit;
        BufferedReader userInput = new BufferedReader (new InputStreamReader(System.in));
        
        try
        {
          Class.forName("com.mysql.jdbc.Driver");
            urlValue="jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
            Connection conn = DriverManager.getConnection(urlValue);
            Statement st = conn.createStatement();
            
            System.out.println("Masukan CIF yang ingin di hapus : ");
            cari_cif = isi.nextLine();
            ResultSet rs = st.executeQuery("UPDATE nasabah SET nasabah.status_N= 0  WHERE nasabah.cif= "+cari_cif+"' ;");
             while(rs.next())
            {
                no = no+1;
                System.out.println("CIF "+rs.getString("CIF")+" Terhapus");
                
            }
            
            if(no==0)
            {
                System.out.println("CIF "+cari_cif+" Tidak ditemukan");
            }
          
              st.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver tidak ditemukan");
            
            
        }
    }
    void hps_karyawan()
    {
         Scanner isi = new Scanner(System.in);
        String cari_nip = null;
        int no = 0;
        int pilihan_edit;
        BufferedReader userInput = new BufferedReader (new InputStreamReader(System.in));
        
        try
        {
          Class.forName("com.mysql.jdbc.Driver");
            urlValue="jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
            Connection conn = DriverManager.getConnection(urlValue);
            Statement st = conn.createStatement();
            
            System.out.println("Masukan nip yang ingin di hapus : ");
            cari_nip = isi.nextLine();
            ResultSet rs = st.executeQuery("UPDATE karyawan SET karyawan.status_K = 0  WHERE karyawan.nip= "+cari_nip+"' ;");
             while(rs.next())
            {
                no = no+1;
                System.out.println("nip "+rs.getString("nip")+" Terhapus");
                
            }
            
            if(no==0)
            {
                System.out.println("nip "+cari_nip+" Tidak ditemukan");
            }
          
              st.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver tidak ditemukan");
            
            
        }
    }
    void hps_jaminan()
    {
        Scanner isi = new Scanner(System.in);
        String cari_kodep = null;
        int no = 0;
        int pilihan_edit;
        BufferedReader userInput = new BufferedReader (new InputStreamReader(System.in));
        
        try
        {
          Class.forName("com.mysql.jdbc.Driver");
            urlValue="jdbc:mysql://"+host+"/"+db+"?user="+user+"&password="+pwd;
            Connection conn = DriverManager.getConnection(urlValue);
            Statement st = conn.createStatement();
            
            System.out.println("Masukan Kode Properti yang ingin di hapus : ");
            cari_kodep = userInput.readLine();
            ResultSet rs = st.executeQuery("SELECT * from Jaminan where kode_properti = '"+cari_kodep+"' ;");
            
            while(rs.next())
            {
                no = no+1;
                System.out.println("Kode properti "+rs.getString("kode_properti")+" Ditemukan, Anda yakin akan menghapus? (Y/N)");
                String pilihan = userInput.readLine();
                if(pilihan.equals("Y"))
                {
                    PreparedStatement pStatement = null;
                    String sql = "UPDATE jaminan SET status_j = 0 WHERE kode_properti = '"+cari_kodep+"' ;";
                    pStatement = conn.prepareStatement(sql);
                    pStatement.setString(1,cari_kodep);

                    int intBaris=pStatement.executeUpdate();
                    if (intBaris>0)
                    {
                        System.out.println("Berhasil menghapus data");
                    } 
                }
                else
                {
                    System.out.println("Data tidak terhapus, Kembali ke menu Delete? (Y/N)");
                    String tahan = isi.nextLine();
                    if(tahan.equals("Y"))
                    {
                        delete();
                    }
                    else
                    {
                        System.exit(0);
                    }
                }
                
            }
            
            if(no==0)
            {
                System.out.println("kode properti "+cari_kodep+" Tidak ditemukan");
            }
            String tahan = isi.nextLine();
            if(tahan.equals("Y"))
            {
                utama();
            }
            else
            {
                System.exit(0);
            }         
            st.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver tidak ditemukan");
        } catch (IOException ex) {
            Logger.getLogger(ProgramPinjamanBank.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    void keluar()
    {
        System.exit(0);
    }
}    
