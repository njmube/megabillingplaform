(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Config_pathrootfilesNewController', Config_pathrootfilesNewController);

    Config_pathrootfilesNewController.$inject = ['$scope', '$stateParams', 'entity', 'Config_pathrootfile'];

    function Config_pathrootfilesNewController ($scope, $stateParams, entity, Config_pathrootfile) {
        var vm = this;
        vm.config_pathrootfile = entity;
        vm.load = function() {
            Config_pathrootfile.get({id : 0}, function(result) {
                vm.config_pathrootfile = result;
            });
        };

        var onSaveSuccess = function (result) {
            vm.config_pathrootfile = result;
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            Config_pathrootfile.update(vm.config_pathrootfile, onSaveSuccess, onSaveError);
        };

    }
})();

