(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('AsnSATController', AsnSATController);

    AsnSATController.$inject = ['$uibModalInstance', 'Principal'];

    function AsnSATController ($uibModalInstance, Principal) {
        var vm = this;
        vm.user = {};
        vm.account = null;
        var today = new Date();
        load();

        function load(){
            Principal.identity().then(function() {

            });
        }

        vm.close = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
