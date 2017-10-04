## CorePatch

An universal patch generator and applier such as using BsDiff/BsPatch and Google Archive Patch.

## Changelog

See details in [CHANGELOG](https://github.com/lizhangqu/CorePatch/blob/master/CHANGELOG.md).

## Examples

I have provided a sample. It is based on unit test.

See sample [here on Github](https://github.com/lizhangqu/CorePatch/tree/master/app/src/test/java/io/github/lizhangqu/corepatch/sample).

To run the test cases, simply clone this repository, use android studio to compile and run

## Usage

### Dependency

**gradle**

```
dependencies {
    //for generator
    compile "io.github.lizhangqu:corepatch-core-generator:1.0.1"
    //for applier
    compile "io.github.lizhangqu:corepatch-core-applier:1.0.1"
}
```

**maven**

```
//for generator
<dependencies>
    <dependency>
      <groupId>io.github.lizhangqu</groupId>
      <artifactId>corepatch-core-generator</artifactId>
      <version>1.0.1</version>
    </dependency>
</dependencies>

//for applier
<dependencies>
    <dependency>
      <groupId>io.github.lizhangqu</groupId>
      <artifactId>corepatch-core-applier</artifactId>
      <version>1.0.1</version>
    </dependency>
</dependencies>
```

### Generator

```
//get generator

Generator generator = CoreGenerator.getInstance().getGenerator(CoreGeneratorType.BS);
//Generator generator = CoreGenerator.getInstance().getGenerator(CoreGeneratorType.ARCHIVE);
//Generator generator = CoreGenerator.getInstance().getGenerator(CoreGeneratorType.TOTAL);
//Generator generator = CoreGenerator.getInstance().getGenerator("BS");
//Generator generator = CoreGenerator.getInstance().getGenerator("ARCHIVE");
//Generator generator = CoreGenerator.getInstance().getGenerator("TOTAL");

//generate

generator.generate(oldFile, newFile, toGeneratedPatchFile);
//generator.generate(oldFile, newFile, toGeneratedPatchOutputStream);

//md5

String md5 = generator.calculateMD5(toGeneratedPatchFile);
```

### Applier

```
//get applier

 Applier applier = CoreApplier.getInstance().getApplier(CoreApplierType.BS);
//Applier applier = CoreApplier.getInstance().getApplier(CoreApplierType.ARCHIVE);
//Applier applier = CoreApplier.getInstance().getApplier(CoreApplierType.TOTAL);
//Applier applier = CoreApplier.getInstance().getApplier("BS");
//Applier applier = CoreApplier.getInstance().getApplier("ARCHIVE");
//Applier applier = CoreApplier.getInstance().getApplier("TOTAL");

//apply

applier.apply(oldFile, pacthFile, toGeneratedNewFile);
//applier.apply(oldFile, pacthInputStream, toGeneratedNewOutputStream);

//md5
String md5 = applier.calculateMD5(toGeneratedNewFile);
```


## License

CorePatch is under the BSD license. See the [LICENSE](https://github.com/lizhangqu/CorePatch/blob/master/LICENSE) file for details.