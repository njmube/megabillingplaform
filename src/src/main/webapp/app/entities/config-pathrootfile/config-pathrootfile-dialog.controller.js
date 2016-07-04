(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Config_pathrootfileDialogController', Config_pathrootfileDialogController);

    Config_pathrootfileDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Config_pathrootfile'];

    function Config_pathrootfileDialogController ($scope, $stateParams, $uibModalInstance, entity, Config_pathrootfile) {
        var vm = this;
        vm.config_pathrootfile = entity;
        vm.load = function(id) {
            Config_pathrootfile.get({id : id}, function(result) {
                vm.config_pathrootfile = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:config_pathrootfileUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.config_pathrootfile.id !== null) {
                Config_pathrootfile.update(vm.config_pathrootfile, onSaveSuccess, onSaveError);
            } else {
                Config_pathrootfile.save(vm.config_pathrootfile, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
