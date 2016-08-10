(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_speiDetailController', Freecom_speiDetailController);

    Freecom_speiDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_spei', 'Free_cfdi'];

    function Freecom_speiDetailController($scope, $rootScope, $stateParams, entity, Freecom_spei, Free_cfdi) {
        var vm = this;

        vm.freecom_spei = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_speiUpdate', function(event, result) {
            vm.freecom_spei = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
