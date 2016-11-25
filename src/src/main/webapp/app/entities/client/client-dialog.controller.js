(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ClientDialogController', ClientDialogController);

    ClientDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Client', 'Client_address'];

    function ClientDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Client, Client_address) {
        var vm = this;
        vm.client = entity;
        vm.client_addresss = Client_address.query({filter: 'client-is-null'});
        $q.all([vm.client.$promise, vm.client_addresss.$promise]).then(function() {
            if (!vm.client.client_address || !vm.client.client_address.id) {
                return $q.reject();
            }
            return Client_address.get({id : vm.client.client_address.id}).$promise;
        }).then(function(client_address) {
            vm.client_addresses.push(client_address);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:clientUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.client.id !== null) {
                Client.update(vm.client, onSaveSuccess, onSaveError);
            } else {
                Client.save(vm.client, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
