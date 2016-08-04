(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_paybill_conceptDetailController', Freecom_paybill_conceptDetailController);

    Freecom_paybill_conceptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_paybill_concept', 'Freecom_storeroom_paybill'];

    function Freecom_paybill_conceptDetailController($scope, $rootScope, $stateParams, entity, Freecom_paybill_concept, Freecom_storeroom_paybill) {
        var vm = this;
        vm.freecom_paybill_concept = entity;
        vm.load = function (id) {
            Freecom_paybill_concept.get({id: id}, function(result) {
                vm.freecom_paybill_concept = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_paybill_conceptUpdate', function(event, result) {
            vm.freecom_paybill_concept = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
