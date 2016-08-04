(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_storeroom_paybillDetailController', Freecom_storeroom_paybillDetailController);

    Freecom_storeroom_paybillDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_storeroom_paybill', 'Free_cfdi'];

    function Freecom_storeroom_paybillDetailController($scope, $rootScope, $stateParams, entity, Freecom_storeroom_paybill, Free_cfdi) {
        var vm = this;
        vm.freecom_storeroom_paybill = entity;
        vm.load = function (id) {
            Freecom_storeroom_paybill.get({id: id}, function(result) {
                vm.freecom_storeroom_paybill = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_storeroom_paybillUpdate', function(event, result) {
            vm.freecom_storeroom_paybill = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
