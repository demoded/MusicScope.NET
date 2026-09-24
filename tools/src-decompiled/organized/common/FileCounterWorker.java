/*
 * Decompiled with CFR 0.152.
 */
package sdfgjkljljoftrytrszgijpokjprs;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;
import sdfgjkljljoftrytrszgijpokjprs.BaseFileWorker;
import sdfgjkljljoftrytrszgijpokjprs.FileLoaderWorker;
import sdfgjkljljoftrytrszgijpokjprs.ILoadingDialog;

public class FileCounterWorker<T>
extends BaseFileWorker<T> {
    public FileCounterWorker(File file, int n, ILoadingDialog uqIWpQkdtRoGxqvvNunoXtF2) {
        super(file, n, null, null, uqIWpQkdtRoGxqvvNunoXtF2);
    }

    @Override
    public void run() {
        this.DSP(this.DSP);
    }

    private void DSP(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            try {
                Files.walkFileTree(file.toPath(), (FileVisitor<? super Path>)new FileVisitor<Path>(){

                    public FileVisitResult DSP(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                        return FileCounterWorker.this.responseView ? FileVisitResult.CONTINUE : FileVisitResult.TERMINATE;
                    }

                    public FileVisitResult FFT(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                        if (basicFileAttributes.isRegularFile() && FileCounterWorker.this.AudioFileExtension != null) {
                            FileCounterWorker.this.AudioFileExtension.DSP(1);
                        }
                        return FileCounterWorker.this.responseView ? FileVisitResult.CONTINUE : FileVisitResult.TERMINATE;
                    }

                    public FileVisitResult DSP(Path path, IOException iOException) throws IOException {
                        return FileCounterWorker.this.responseView ? FileVisitResult.CONTINUE : FileVisitResult.TERMINATE;
                    }

                    public FileVisitResult FFT(Path path, IOException iOException) throws IOException {
                        return FileCounterWorker.this.responseView ? FileVisitResult.CONTINUE : FileVisitResult.TERMINATE;
                    }

                    @Override
                    public /* synthetic */ FileVisitResult postVisitDirectory(Object object, IOException iOException) throws IOException {
                        return this.FFT((Path)object, iOException);
                    }

                    @Override
                    public /* synthetic */ FileVisitResult visitFileFailed(Object object, IOException iOException) throws IOException {
                        return this.DSP((Path)object, iOException);
                    }

                    @Override
                    public /* synthetic */ FileVisitResult visitFile(Object object, BasicFileAttributes basicFileAttributes) throws IOException {
                        return this.FFT((Path)object, basicFileAttributes);
                    }

                    @Override
                    public /* synthetic */ FileVisitResult preVisitDirectory(Object object, BasicFileAttributes basicFileAttributes) throws IOException {
                        return this.DSP((Path)object, basicFileAttributes);
                    }
                });
            }
            catch (IOException iOException) {
                Logger.getLogger(FileLoaderWorker.class.getName()).log(Level.SEVERE, null, iOException);
            }
        } else if (this.AudioFileExtension != null) {
            this.AudioFileExtension.DSP(1);
        }
    }
}

