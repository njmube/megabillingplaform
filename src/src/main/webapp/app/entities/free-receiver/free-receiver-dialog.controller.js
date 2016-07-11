(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_receiverDialogController', Free_receiverDialogController);

    Free_receiverDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Free_receiver', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code', 'Type_taxpayer'];

    function Free_receiverDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Free_receiver, C_country, C_state, C_municipality, C_colony, C_zip_code, Type_taxpayer) {
        var vm = this;
        vm.free_receiver = entity;
        vm.c_countries = C_country.query();
        vm.c_states = C_state.query();
        vm.c_municipalities = C_municipality.query();
        vm.c_colonies = C_colony.query();
        vm.c_zip_codes = C_zip_code.query();
        vm.type_taxpayers = Type_taxpayer.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

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
