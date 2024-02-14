//use this kNN function as an example template 

import java.lang.Math;
import java.util.*; 
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class kNN_test
{
 public static void main(String[] args) throws IOException 
 {
    //modified the data to obtain the optimal K value
     int TRAIN_SIZE=400; //no. training patterns 
     //Replaced the VAL_SIze to Test_Size
     int TEST_SIZE=200; //no. testing patterns 
     int FEATURE_SIZE=61; //no. of features
    //This section loads the data and is similar to the classwork
     double[][] train = new double[TRAIN_SIZE][FEATURE_SIZE]; //training data 
     String train_file = "alco_train_data.txt"; //read training data
        try (Scanner tmp = new Scanner(new File(train_file))) {
            for (int i = 0; i < TRAIN_SIZE; i++)
                for (int j = 0; j < FEATURE_SIZE; j++)
                    if (tmp.hasNextDouble())
                        train[i][j] = tmp.nextDouble();
            tmp.close();
        }
     double[][] test = new double[TEST_SIZE][FEATURE_SIZE]; //validation data
     String val_file = "alco_test_data.txt"; //read test data
        try (Scanner tmp = new Scanner(new File(val_file))) {
            for (int i = 0; i < TEST_SIZE; i++)
                for (int j = 0; j < FEATURE_SIZE; j++)
                    if (tmp.hasNextDouble())
                        test[i][j] = tmp.nextDouble();
            tmp.close();
        }
     int[] train_label= new int[TRAIN_SIZE]; //actual target/class label for train data
     String train_label_file = "alco_train_label.txt"; //read train label
        try (Scanner tmp = new Scanner(new File(train_label_file))) {
            for (int i = 0; i < TRAIN_SIZE; i++)
                if (tmp.hasNextInt())
                    train_label[i] = tmp.nextInt();
            tmp.close();
        }
     //int[] val_label= {0,1}; //actual target/class label for validation data 
     
      
     //System.out.println("Distance(train,test):");
  
     double[][] dist_label = new double[TRAIN_SIZE][2]; //distance array, no of columns is increased by 1 to accomodate distance
     double[] y = new double[FEATURE_SIZE]; //temp variable for validation data
     double[] x = new double[FEATURE_SIZE]; //temp variable for train data
     
     //This variable stores the optimalK values
     int NUM_NEIGHBOUR = 9; //k value in kNN
     int[] neighbour = new int[NUM_NEIGHBOUR];
     int[] predicted_class = new int[TEST_SIZE];
         
       for (int j=0; j<TEST_SIZE; j++) //for every validation data
       {
          for (int f=0; f<FEATURE_SIZE; f++)
          y[f]=test[j][f]; 

            for (int i=0; i<TRAIN_SIZE; i++)
            {
              for (int f=0; f<FEATURE_SIZE; f++)
              x[f]=train[i][f]; 
       
            double sum=0.0;
            for (int f=0; f<FEATURE_SIZE; f++)
            //Instead of using Euclidean distance we instead have to use manhattan distance
            //sum = sum+Math.abs(x[f]-y[f]);
            sum=sum + ((x[f]-y[f])*(x[f]-y[f])); //Euclidean distance
       
            dist_label[i][0] = Math.sqrt(sum); //Euclidean distance
            //Since we are using the manhattan distance we do not need to sqrt the values
            //dist_label[i][0]=sum;
            dist_label[i][1] = train_label[i]; //add the target label
       
            //System.out.println(dist_label[i][0] + " " + dist_label[i][1]);
            }
   
          Sort(dist_label,1); //sorting
    
        //   System.out.println();
        //   System.out.println("After sorting");
        //   for(int i = 0; i< TRAIN_SIZE; i++) 
        //   {
        //     for (int k = 0; k < 2; k++)
        //     System.out.print(dist_label[i][k] + " ");
        //     System.out.println();
        //   }
    
          for (int n=0; n<NUM_NEIGHBOUR; n++)
          { //training label from required neighbours
          neighbour[n]=(int) dist_label[n][1];
        }

        //   System.out.println();
        //   System.out.println("Neighbours after sorting");
        
        //   for(int n = 0; n<optimalK; n++) 
        //     System.out.print(neighbour[n] + " ");
     
        //   System.out.println();
        
        predicted_class[j]=Mode(neighbour);
        // System.out.print("Predicted class = " + predicted_class[j]);
        // System.out.println(); System.out.println();
     
        } //end test data loop
       
       //accuracy computation 
       //only if labels are provided, eg for validation data
       //disable if using test data 
    //    int success=0;
    //    //for (int j=0; j<TEST_SIZE; j++)
    //     // if (predicted_class[j]==val_label[j])
    //     // success=success+1;
    //    double accuracy=(success*100.0)/TEST_SIZE;
    //    System.out.print("Accuracy = " + accuracy);
           
       //writing kNN_output.txt in the required format 
      try
      {
      PrintWriter writer = new PrintWriter("kNN_output.txt", "UTF-8");
        for(int j=0; j<TEST_SIZE; j++) 
        writer.print(predicted_class[j] + " ");
      writer.close();
      }
      catch(Exception e)
      {
      System.out.println(e);
      }

  } //end main loop

 
 public static void Sort (double[][] sort_array, final int column_sort) //sorting function
 {
 Arrays.sort(sort_array, new Comparator<double[]>() 
  {
    @Override
    public int compare(double[] a, double[] b) 
    {
    if(a[column_sort-1] > b[column_sort-1]) return 1;
      else return -1;
    }
  });
}
     
 
 public static int Mode(int neigh[]) //function to find mode
 {
  int modeVal=0;
  int maxCnt=0;
     
  for (int i = 0; i < neigh.length; ++i) 
    {
    int count = 0;
      for (int j = 0; j < neigh.length; ++j)
      {
        if (neigh[j] == neigh[i]) 
        count=count+1;
      }
        if (count > maxCnt) 
        {
        maxCnt = count;
        modeVal = neigh[i];
        }
    }

  return modeVal;
 }
 
 
} //end class loop
