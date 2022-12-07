package com.dmj.Netview.utils;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UploadObject {
    public static void uploadObject(
            String projectId, String bucketName, String objectName, String filePath) throws IOException {

        Storage.BlobTargetOption precondition = Storage.BlobTargetOption.doesNotExist();

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        // añadimos el tipo de contenido que va a subir al storage
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("video/mp4").build();
        storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)), precondition);
        storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        System.out.println(
                "PRUEBA /t/t/t" + "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
    }
}
