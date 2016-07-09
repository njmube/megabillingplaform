(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Config_pathrootfileNewController', Config_pathrootfileNewController);

    Config_pathrootfileNewController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Config_pathrootfile'];

    function Config_pathrootfileNewController ($scope, $stateParams, $uibModalInstance, entity, Config_pathrootfile) {
        var vm = this;
        vm.config_pathrootfile = entity;
        vm.load = function() {
            Config_pathrootfile.get({id : 0}, function(result) {
                vm.config_pathrootfile = result;
            });
        };

        var onSaveSuccess = function (result) {
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

