package com.amazon.prime.hackathon;

import com.algorithmia.APIException;
import com.algorithmia.AlgorithmException;
import com.algorithmia.Algorithmia;
import com.algorithmia.AlgorithmiaClient;
import com.algorithmia.algo.AlgoResponse;
import com.algorithmia.algo.Algorithm;

import java.util.concurrent.TimeUnit;

public class RetrieveEntities {
    public static void main(String[] args) throws APIException, AlgorithmException {
        String input = "take me to the red car scene";
        AlgorithmiaClient client = Algorithmia.client("simnuFI52645C/NN23Bo4W4MkXa1");
        Algorithm algo = client.algo("nlp/AutoTag/1.0.1");
        algo.setTimeout(300L, TimeUnit.SECONDS); //optional
        AlgoResponse result = algo.pipe(input);
        System.out.println(result.asJsonString());
    }
}
