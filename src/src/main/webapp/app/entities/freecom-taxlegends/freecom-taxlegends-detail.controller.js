(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_taxlegendsDetailController', Freecom_taxlegendsDetailController);

    Freecom_taxlegendsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_taxlegends', 'Free_cfdi'];

    function Freecom_taxlegendsDetailController($scope, $rootScope, $stateParams, entity, Freecom_taxlegends, Free_cfdi) {
        var vm = this;
        vm.freecom_taxlegends = entity;
        vm.load = function (id) {
            Freecom_taxlegends.get({id: id}, function(result) {
                vm.freecom_taxlegends = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_taxlegendsUpdate', function(event, result) {
            vm.freecom_taxlegends = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
