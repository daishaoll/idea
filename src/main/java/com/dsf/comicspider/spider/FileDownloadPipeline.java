package com.dsf.comicspider.spider;

import com.dsf.comicspider.service.ComicNumberImpl;
import com.dsf.comicspider.service.ComicNumberService;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.*;
import java.util.Map;
import java.util.UUID;

/**
 * @author 戴少峰
 * @version 1.0
 * @className FilePipeline
 * @date 2021/4/29-16:49
 */
public class FileDownloadPipeline extends FilePersistentBase implements Pipeline {



    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            File file = getFile(entry.getKey());
            try (OutputStream outputStream = new FileOutputStream(file)
            ) {
                outputStream.write((byte[])entry.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
