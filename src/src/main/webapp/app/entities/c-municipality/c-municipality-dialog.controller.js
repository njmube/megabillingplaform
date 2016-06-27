(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_municipalityDialogController', C_municipalityDialogController);

    C_municipalityDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'C_municipality', 'C_state', 'C_location'];

    function C_municipalityDialogController ($scope, $stateParams, $uibModalInstance, entity, C_municipality, C_state, C_location) {
        var vm = this;
        vm.c_municipality = entity;
        vm.c_states = C_state.query({countryId:-1});
        vm.c_locations = C_location.query({municipalityId:-1});
        vm.load = function(id) {
            C_municipality.get({id : id}, function(result) {
                vm.c_municipality = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:c_municipalityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.c_municipality.id !== null) {
                C_municipality.update(vm.c_municipality, onSaveSuccess, onSaveError);
            } else {
                C_municipality.save(vm.c_municipality, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
