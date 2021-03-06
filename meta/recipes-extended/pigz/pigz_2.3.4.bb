SUMMARY = "A parallel implementation of gzip"
DESCRIPTION = "pigz, which stands for parallel implementation of gzip, is a \
fully functional replacement for gzip that exploits multiple processors and \
multiple cores to the hilt when compressing data. pigz was written by Mark \
Adler, and uses the zlib and pthread libraries."
HOMEPAGE = "http://zlib.net/pigz/"
SECTION = "console/utils"
LICENSE = "Zlib & Apache-2.0"
LIC_FILES_CHKSUM = "file://pigz.c;beginline=7;endline=21;md5=a21d4075cb00ab4ca17fce5e7534ca95"

PROVIDES_class-native += "gzip-native"

SRC_URI = "https://github.com/madler/pigz/archive/v${PV}.tar.gz;downloadfilename=${BP}.tar.gz"
SRC_URI[md5sum] = "c109057050b15edf3eb9bb4d0805235e"
SRC_URI[sha256sum] = "763f2fdb203aa0b7b640e63385e38e5dd4e5aaa041bc8e42aa96f2ef156b06e8"

UPSTREAM_CHECK_URI = "http://zlib.net/${BPN}/"
UPSTREAM_CHECK_REGEX = "pigz-(?P<pver>.*)\.tar"

DEPENDS = "zlib"

EXTRA_OEMAKE = "-e MAKEFLAGS="

inherit update-alternatives

do_install_class-target() {
	# Install files into /bin (FHS), which is typical place for gzip
	install -d ${D}${base_bindir}
	install ${B}/pigz ${D}${base_bindir}/pigz
	ln -nsf pigz ${D}${base_bindir}/unpigz
	ln -nsf pigz ${D}${base_bindir}/pigzcat
}

do_install() {
	install -d ${D}${bindir}
	install ${B}/pigz ${D}${bindir}/gzip
	ln -nsf gzip ${D}${bindir}/gunzip
	ln -nsf gzip ${D}${bindir}/zcat
}

ALTERNATIVE_PRIORITY = "80"
ALTERNATIVE_${PN} = "gunzip gzip zcat"
ALTERNATIVE_${PN}_class-nativesdk = ""
ALTERNATIVE_LINK_NAME[gunzip] = "${base_bindir}/gunzip"
ALTERNATIVE_LINK_NAME[gzip] = "${base_bindir}/gzip"
ALTERNATIVE_LINK_NAME[zcat] = "${base_bindir}/zcat"
ALTERNATIVE_TARGET = "${base_bindir}/pigz"

BBCLASSEXTEND = "native nativesdk"
