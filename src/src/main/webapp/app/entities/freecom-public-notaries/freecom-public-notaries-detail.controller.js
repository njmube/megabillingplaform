(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_public_notariesDetailController', Freecom_public_notariesDetailController);

    Freecom_public_notariesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_public_notaries', 'Free_cfdi'];

    function Freecom_public_notariesDetailController($scope, $rootScope, $stateParams, entity, Freecom_public_notaries, Free_cfdi) {
        var vm = this;
        vm.freecom_public_notaries = entity;
        vm.load = function (id) {
            Freecom_public_notaries.get({id: id}, function(result) {
                vm.freecom_public_notaries = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_public_notariesUpdate', function(event, result) {
            vm.freecom_public_notaries = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
