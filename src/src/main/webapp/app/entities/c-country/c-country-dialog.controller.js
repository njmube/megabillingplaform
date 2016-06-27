(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_countryDialogController', C_countryDialogController);

    C_countryDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_country', 'C_state'];

    function C_countryDialogController ($scope, $stateParams, $uibModalInstance, entity, C_country, C_state) {
        var vm = this;
        vm.c_country = entity;
        vm.c_states = C_state.query({countryId:0});
        vm.load = function(id) {
            C_country.get({id : id}, function(result) {
                vm.c_country = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_countryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_country.id !== null) {
                C_country.update(vm.c_country, onSaveSuccess, onSaveError);
            } else {
                C_country.save(vm.c_country, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
