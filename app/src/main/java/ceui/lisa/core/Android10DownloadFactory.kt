package ceui.lisa.core

import android.content.Context
import android.net.Uri
import android.os.Environment
import ceui.lisa.activities.Shaft
import ceui.lisa.download.FileCreator
import ceui.lisa.utils.Common
import okhttp3.Response
import rxhttp.wrapper.callback.UriFactory

class Android10DownloadFactory constructor(
        context: Context,
        private val item: DownloadItem,
) : UriFactory(context) {

    lateinit var fileUri: Uri

    override fun insert(response: Response): Uri {
        if (item.illust.isGif) {
            val file = SAFile.createZipFile(context, item.name)
            fileUri = Uri.fromFile(file)
            return fileUri
        } else {
            if (Common.isAndroidQ()) {
                val documentFile = SAFile.getDocument(context, item.illust, item.index)
                fileUri = documentFile.uri
                return fileUri
            } else {
                val file = FileCreator.createIllustFile(item.illust, item.index)
                fileUri = Uri.fromFile(file)
                return fileUri
            }
        }

    }
}