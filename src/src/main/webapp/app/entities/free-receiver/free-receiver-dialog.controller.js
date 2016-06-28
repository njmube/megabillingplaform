(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_receiverDialogController', Free_receiverDialogController);

    Free_receiverDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Free_receiver', 'C_country', 'C_state', 'C_municipality', 'C_location', 'C_colony', 'C_zip_code'];

    function Free_receiverDialogController ($scope, $stateParams, $uibModalInstance, entity, Free_receiver, C_country, C_state, C_municipality, C_location, C_colony, C_zip_code) {
        var vm = this;
        vm.free_receiver = entity;
        vm.c_countrys = C_country.query();
        vm.c_states = C_state.query();
        vm.c_municipalitys = C_municipality.query();
        vm.c_locations = C_location.query();
        vm.c_colonys = C_colony.query();
        vm.c_zip_codes = C_zip_code.query();
        vm.load = function(id) {
            Free_receiver.get({id : id}, function(result) {
                vm.free_receiver = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:free_receiverUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.free_receiver.id !== null) {
                Free_receiver.update(vm.free_receiver, onSaveSuccess, onSaveError);
            } else {
                Free_receiver.save(vm.free_receiver, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.create_date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
