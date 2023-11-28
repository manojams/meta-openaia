SECTION = "bootloaders"
DESCRIPTION = "U-Boot 2023.07 neu6b-uboot"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

require recipes-bsp/u-boot/u-boot.inc

SRC_URI += " \
	git://git@github.com/openaia/u-boot.git;protocol=ssh;branch="ncm6b" \
	file://rk3588_bl31_v1.40.elf \
	file://rk3588_ddr_lp4_2112MHz_lp5_2736MHz_v1.12.bin \
	"

SRCREV = "f0554631cbf0a3f72343ad36e9042787c1b5813e"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
do_configure[cleandirs] = "${B}"


# copy rk3588 prebuild binary to u-boot dir.
do_configure:prepend () {
      install -m 644 ${WORKDIR}/rk3588_ddr_lp4_2112MHz_lp5_2736MHz_v1.12.bin ${B}/rk3588_ddr_lp4_2112MHz_lp5_2736MHz_v1.12.bin
      install -m 644 ${WORKDIR}/rk3588_bl31_v1.40.elf ${B}/rk3588_bl31_v1.40.elf
}

EXTRA_OEMAKE:append:rk3588 = " BL31=${B}/rk3588_bl31_v1.40.elf"
EXTRA_OEMAKE:append:rk3588 = " ROCKCHIP_TPL=${B}/rk3588_ddr_lp4_2112MHz_lp5_2736MHz_v1.12.bin"

DEPENDS += " flex-native bison-native bc-native dtc-native python3-setuptools-native python3-pyelftools-native"
