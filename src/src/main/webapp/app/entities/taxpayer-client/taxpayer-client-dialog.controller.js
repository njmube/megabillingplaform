(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_clientDialogController', Taxpayer_clientDialogController);

    Taxpayer_clientDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Taxpayer_client', 'Client_address', 'Taxpayer_account'];

    function Taxpayer_clientDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Taxpayer_client, Client_address, Taxpayer_account) {
        var vm = this;

        vm.taxpayer_client = entity;
        vm.clear = clear;
        vm.save = save;
        vm.client_addresss = Client_address.query({filter: 'taxpayer_client-is-null'});
        $q.all([vm.taxpayer_client.$promise, vm.client_addresss.$promise]).then(function() {
            if (!vm.taxpayer_client.client_address || !vm.taxpayer_client.client_address.id) {
                return $q.reject();
            }
            return Client_address.get({id : vm.taxpayer_client.client_address.id}).$promise;
        }).then(function(client_address) {
            vm.client_addresses.push(client_address);
        });
        vm.taxpayer_accounts = Taxpayer_account.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.taxpayer_client.id !== null) {
                Taxpayer_client.update(vm.taxpayer_client, onSaveSuccess, onSaveError);
            } else {
                Taxpayer_client.save(vm.taxpayer_client, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:taxpayer_clientUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
