(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Request_taxpayer_accountDialogController', Request_taxpayer_accountDialogController);

    Request_taxpayer_accountDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Request_taxpayer_account', 'Request_state', 'Tax_address_request', 'User'];

    function Request_taxpayer_accountDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Request_taxpayer_account, Request_state, Tax_address_request, User) {
        var vm = this;

        vm.request_taxpayer_account = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.request_states = Request_state.query({filtername: " "});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.request_taxpayer_account.id !== null) {
                Request_taxpayer_account.update(vm.request_taxpayer_account, onSaveSuccess, onSaveError);
            } else {
                Request_taxpayer_account.save(vm.request_taxpayer_account, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:request_taxpayer_accountUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date_request = false;
        vm.datePickerOpenStatus.date_born = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
