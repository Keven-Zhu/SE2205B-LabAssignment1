import java.io.*;

public class Assignment1{


    public int[][] denseMatrixMult(int[][] A, int[][] B, int size)
    {
        int[][] result = new int[size][size];

        if (size == 1){
            result[0][0] = A[0][0]*B[0][0];
            return result;
        }
        else{
            int[][] a00 = new int[size/2][size/2];
            int[][] a01 = new int[size/2][size/2];
            int[][] a10 = new int[size/2][size/2];
            int[][] a11 = new int[size/2][size/2];

            int[][] b00 = new int[size/2][size/2];
            int[][] b01 = new int[size/2][size/2];
            int[][] b10 = new int[size/2][size/2];
            int[][] b11 = new int[size/2][size/2];

            for (int a = 0, b = size/2; a < size/2; a++, b++){
                for (int c = 0, d = size/2; c < size/2; c++, d++){
                    a00[a][c] = A[a][c];//a
                    a01[a][c] = A[a][d];//b
                    a10[a][c] = A[b][c];//c
                    a11[a][c] = A[b][d];//d

                    b00[a][c] = B[a][c];//e
                    b01[a][c] = B[a][d];//f
                    b10[a][c] = B[b][c];//g
                    b11[a][c] = B[b][d];//h
                }
            }

            int[][]m0 = denseMatrixMult(sum(a00,a11,0,0,0,0,size/2),sum(b00,b11,0,0,0,0,size/2),size/2);
            int[][]m1 = denseMatrixMult(sum(a10,a11,0,0,0,0,size/2),b00,size/2);
            int[][]m2 = denseMatrixMult(a00,sub(b01,b11,0,0,0,0,size/2),size/2);
            int[][]m3 = denseMatrixMult(a11,sub(b10,b00,0,0,0,0,size/2),size/2);
            int[][]m4 = denseMatrixMult(sum(a00,a01,0,0,0,0,size/2),b11,size/2);
            int[][]m5 = denseMatrixMult(sub(a10,a00,0,0,0,0,size/2),sum(b00,b01,0,0,0,0,size/2),size/2);
            int[][]m6 = denseMatrixMult(sub(a01,a11,0,0,0,0,size/2),sum(b10,b11,0,0,0,0,size/2),size/2);

            int[][]c00 = sum(sub(sum(m0,m3,0,0,0,0,size/2),m4,0,0,0,0,size/2),m6,0,0,0,0,size/2);
            int[][]c01 = sum(m2,m4,0,0,0,0,size/2);
            int[][]c10 = sum(m1,m3,0,0,0,0,size/2);
            int[][]c11 = sum(sub(sum(m0,m2,0,0,0,0,size/2),m1,0,0,0,0,size/2),m5,0,0,0,0,size/2);

            for (int a = 0, b = size/2; a < size/2; a++, b++) {
                for (int c = 0, d = size / 2; c < size / 2; c++, d++) {
                    result[a][c] = c00[a][c];
                    result[a][d] = c01[a][c];
                    result[b][c] = c10[a][c];
                    result[b][d] = c11[a][c];
                }
            }

            return result;
        }
    }

    public int[][] sum(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int n){
        int[][] m1 = new int[n][n];
        int[][] m2 = new int[n][n];

        for (int i = 0; i < n ; i++){
            for (int j = 0; j<n;j++){
                m1[i][j] = A[x1+i][y1+j];
            }
        }

        for (int i = 0; i < n ; i++){
            for (int j = 0; j<n;j++){
                m2[i][j] = B[x2+i][y2+j];
            }
        }

        for (int i = 0; i < n;i++){
            for (int j = 0; j<n;j++){
                m1[i][j] = m1[i][j] + m2[i][j];
            }
        }

        return m1;
    }

    public int[][] sub(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int n){
        int[][] m1 = new int[n][n];
        int[][] m2 = new int[n][n];

        for (int i = 0; i < n ; i++){
            for (int j = 0; j<n;j++){
                m1[i][j] = A[x1+i][y1+j];
            }
        }

        for (int i = 0; i < n ; i++){
            for (int j = 0; j<n;j++){
                m2[i][j] = B[x2+i][y2+j];
            }
        }

        for (int i = 0; i < n;i++){
            for (int j = 0; j<n;j++){
                m1[i][j] = m1[i][j] - m2[i][j];
            }
        }

        return m1;
    }


    public int[][] initMatrix(int n){
        int[][] A = new int[n][n];

        return A;
    }

    public void printMatrix(int n, int[][] A)
    {
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                System.out.print(A[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public int[][] readMatrix(String filename, int n) throws Exception {

        String workingDir = (new File(System.getProperty("user.dir"))).getParent();

        File file = new File(workingDir + "\\" + filename);

        BufferedReader br = new BufferedReader(new FileReader(file));

        int[][] out = new int[n][n];
        String s;
        int count = 0;

        while ((s = br.readLine()) != null) {

            String[] data = s.split(" ");

            for (int i = 0; i < n; i++) {
                out[count][i] = Integer.parseInt(data[i]);
            }
            count++;
        }

        return out;
    }
}