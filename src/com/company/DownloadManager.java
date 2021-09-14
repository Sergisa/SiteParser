package com.company;

import okhttp3.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DownloadManager {
    private final OkHttpClient okHttpClient;
    private String folder;
    private List<String> allowedFormats = new ArrayList<>();

    public DownloadManager() {
        okHttpClient = new OkHttpClient.Builder().
                //connectTimeout(15, TimeUnit.SECONDS).
                //readTimeout(20, TimeUnit.SECONDS).
                //writeTimeout(20, TimeUnit.SECONDS).
                        retryOnConnectionFailure(true).
                build();
    }

    public void download(List<URL> urls) {
        Request request;
        for (URL url : urls) {
            request = new Request.Builder()
                    .get()
                    .url(url)
                    .build();
            Call call = okHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!allowedFormats.isEmpty() && allowedFormats.contains(FilenameUtils.getExtension(url.getPath()))) {
                        saveReport(FilenameUtils.getName(url.getPath()), response.body().string());
                    }

                }
            });
        }
    }

    public void saveReport(String filename, byte[] data) throws IOException {
        final Path path = Paths.get(filename);
        FileUtils.writeByteArrayToFile(new File(folder + "/" + filename), data);

    }

    public void saveReport(String filename, String data) throws IOException {
        saveReport(filename, data.getBytes(StandardCharsets.UTF_8));
    }

    public static Builder newBuilder() {
        return new DownloadManager().new Builder();
    }

    public final class Builder {
        public Builder addAllowedFormat(String format) {
            DownloadManager.this.allowedFormats.add(format);
            return this;
        }

        public Builder addAllowedFormats(String[] formats) {
            DownloadManager.this.allowedFormats.addAll(Arrays.asList(formats));
            return this;
        }

        public Builder setAllowedFormats(String[] formats) {
            DownloadManager.this.allowedFormats = Arrays.asList(formats);
            return this;
        }

        public Builder setSaveFolder(File folder) {
            return setSaveFolder(folder.toString());
        }

        public Builder setSaveFolder(String folder) {
            System.out.println("Saving folder is: " + folder);
            DownloadManager.this.folder = folder;
            return this;
        }

        public DownloadManager build() {
            return DownloadManager.this;
        }
    }
}
