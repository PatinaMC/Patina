#!/usr/bin/env node
const child_process = require('child_process');
const util = require('util');
const fs = require('fs');

function execAsync(cmd, callback) {
  const c = child_process.exec(cmd, {shell: 'bash'}, callback);

  c.stdout.pipe(process.stdout);
  c.stderr.pipe(process.stderr);

  return c;
}

const exec = util.promisify(execAsync);
const readFile = util.promisify(fs.readFile);

function realname(type, short) {
  for (var i=0; i<=9999; i++) {
    for (const prefix of ['', '0', '00', '000']) {
      const full = 'patches/'+type+'/'+prefix+i+"-"+short;
      if (fs.existsSync(full)) {
        return full;
      }
    }
  }
  throw new Error(`patch not found type=${type} short=${short}`)
}

function parse(prop) {
  return new Map(prop.replace(/\r/g,"").split("\n").filter(line => line !== "").map(line => {
      const [key, value] = line.split("=");
      return [key, value];
    }));
}

(async () => {
  await exec('rm -fr patches *-Server *-API');
  await exec('git checkout HEAD patches');
  await exec('./gradlew applyPatches');
  const config = parse(await readFile(process.argv.slice().pop(), {encoding: 'utf8'}));
  const patches = config.get('list').split(',').map(element => {
    const [type, short] = element.split('/');
    return realname(type, short);
  });
  const patchesCmd = patches.join(' ');
  const configBlack = config.get('useBlackList');
  const useBlackList = configBlack === 'True' ? true : (configBlack === 'False' ? false : void 0);
  if (useBlackList === void 0) {
    throw new Error('Illegal value');
  }
  if (useBlackList) {
    await exec('rm '+patchesCmd);
  } else {
    await exec('rm -fr patches');
    await exec('git checkout HEAD '+patchesCmd);
  }
  await exec('./gradlew applyPatches');
})();
