(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('CfdiChooseClientDialogController', CfdiChooseClientDialogController);

    CfdiChooseClientDialogController.$inject = ['taxpayer_account_entity', 'Taxpayer_client', '$uibModalInstance'];

    function CfdiChooseClientDialogController (taxpayer_account_entity, Taxpayer_client, $uibModalInstance) {
        var vm = this;

        vm.taxpayer_account = taxpayer_account_entity;
        vm.taxpayer_clients = Taxpayer_client.query({page: 0, size: 30, sort: 'id,asc', taxpayeraccount: vm.taxpayer_account.id, rfc: " ", bussinesname: " ", email: " ", phone: " "});

        vm.clear = clear;
        vm.chooseClient = chooseClient;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function chooseClient (id) {
            vm.isSaving = true;
            Taxpayer_client.get({id: id}, onSuccess, onError);
        }

        function onSuccess(data) {
            $uibModalInstance.close(data);
            vm.isSaving = false;
        }

        function onError () {
            vm.isSaving = false;
        }
    }
})();
