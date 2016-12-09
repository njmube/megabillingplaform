(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_colonyDialogController', C_colonyDialogController);

    C_colonyDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_colony', 'C_municipality', 'C_zip_code'];

    function C_colonyDialogController ($scope, $stateParams, $uibModalInstance, entity, C_colony, C_municipality, C_zip_code) {
        var vm = this;
        vm.c_colony = entity;
        vm.c_municipalitys = C_municipality.query({stateId:0,filtername:" "});
        vm.c_zip_codes = C_zip_code.query({filtername:" "});
        vm.load = function(id) {
            C_colony.get({id : id}, function(result) {
                vm.c_colony = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_colonyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_colony.id !== null) {
                C_colony.update(vm.c_colony, onSaveSuccess, onSaveError);
            } else {
                C_colony.save(vm.c_colony, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
