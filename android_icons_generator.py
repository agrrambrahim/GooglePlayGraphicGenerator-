#!/usr/bin/env python
#
# Android icon generator
#
# Generate different dimension of android icons
# and put it to proper folder.
#
# Author: Ming Chen <ming_c AT hotmail.com>
#
#
# Dependence PIL:
#
#   pip install PIL
#

import os, sys
from PIL import Image

dimensions = (('xxxhdpi', (192, 192)),
              ('xxhdpi', (144, 144)),
              ('xhdpi', (96, 96)),
              ('hdpi', (72, 72)),
              ('ldpi', (32, 32)),
              ('mdpi', (48, 48)))

if len(sys.argv) != 3:
    print ''
    print 'Generate different dimension of android icons and put it to proper folder.'
    print 'Support dimensions: '
    for (resolution, (width, height)) in dimensions:
        print '  %7s %dx%d' % (resolution, width, height)
    print ''
    print 'Usage: %s <raw icon file> <res dir>/<prefix>'
    print '  `raw icon file` is the high resolution icon file.'
    print '  `res dir` is your Android project\'s res dir.'
    print '  `prefix` can be `drawable` or `mipmap`.'
    print ''
    print 'Examples:'
    print '  android_icons_generator.py ic_launcher.png ~/awesome/app/src/main/res/mipmap'
    print '  android_icons_generator.py ic_person.png ~/awesome/app/src/main/res/drawable'
    print ''
    sys.exit(1)

infile = sys.argv[1]    # e.g. ic_launcher.png
prefix = sys.argv[2]    # e.g. <path-to-res>/drawable or <path-to-res>/mipmap

for (resolution, size) in dimensions:
    im = Image.open(infile)
    im.thumbnail(size, Image.ANTIALIAS)
    folderName = '%s-%s' % ('mipmap', resolution)
    out_dir = '%s/%s' % (prefix, folderName)
    if not os.path.exists(out_dir):
        os.mkdir(out_dir)

    outfile = '%s/%s' % (out_dir,'ic_launcher.png')
    print 'Generating %s...' % outfile
    im.save(outfile, "PNG")

print 'Done'
