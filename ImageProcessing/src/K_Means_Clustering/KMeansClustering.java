package K_Means_Clustering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class KMeansClustering {
	private int imgHeight, imgWeight;
	
	public void KMeansClustering(BufferedImage img, int[][] imageMat){
		this.imgHeight = img.getHeight();
		this.imgWeight = img.getWidth();
		
	/*	for(int i=0; i< imgHeight; i++){
			for(int j=0; j< imgWeight; j++){
				System.out.print(imageMat[j][i]+" ");  
			}
			
			System.out.println();
		}*/
		
		int [] K = {40, 60, 90, 150};
		
		clusterPixels(K, imageMat);
	}
	
	public void clusterPixels(int[] k, int[][] imageMat){
		
		int distance = 500;
		
		int[][] clusterGroup = new int[imgWeight][imgHeight];
		
		for(int l = 0; l<10; l++){ // 5 loop for cluster 
			for(int i = 0; i < imgHeight; i++){
				for(int j = 0; j < imgWeight; j++){
					for(int a = 0; a <k.length; a++){
						int tempDis = Math.abs(k[a] - imageMat[j][i]);
						if(tempDis < distance){
							distance = tempDis;
							clusterGroup[j][i] =  a;
						}
					}
					distance = 500;
				}
			}
			
			 k = calculateNewCentroid(clusterGroup, k, imageMat);
			
			 changeClusterPixelsColor(clusterGroup, k.length);
			 
		}
	}
	
	private void changeClusterPixelsColor(int[][] clusterGroup, int clusterLength) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("src/K_Means_Clustering/Images/img_4.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Color[] c = new Color[4];
		c[0] = new Color(255,0,0);
		c[1] = new Color(0,255,0);
		c[2] = new Color(0,0,255);
		c[3] = new Color(255,255,255);
		
		for(int i = 0; i < imgHeight; i++){
			for(int j = 0; j < imgWeight; j++){
				for(int a = 0; a < clusterLength; a++){
					if(clusterGroup[j][i] == a){
						img.setRGB(j, i, c[a].getRGB());
					}
				}
			}
		}
		
		new WriteImage().Write(img, "src/K_Means_Clustering/Images/", "Kmean.jpg");
	}

	public int[] calculateNewCentroid(int [][] clusterGroup, int[] k, int[][] imageMat){
		
		int[][] countClusterSet = new int[k.length][2];
		
		for(int a = 0; a< k.length; a++){
			countClusterSet[a][0] = 0;
			countClusterSet[a][1] = 0;
			//System.out.print(k[a]+" ");
		}
		//System.out.println();
		
		for(int i = 0; i < imgHeight; i++){
			for(int j = 0; j < imgWeight; j++){
				for(int a = 0; a< k.length; a++)
				if(clusterGroup[j][i] == a){
					countClusterSet[a][0]++;
					countClusterSet[a][1] += imageMat[j][i];
				}
			}
		}
		
		for(int a = 0; a< k.length; a++){
			k[a] = countClusterSet[a][1] / countClusterSet[a][0];
			
			System.out.print(k[a]+" ");
		}
		System.out.println("\n");
		
		return k;
	}
}

