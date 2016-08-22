(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_incotermDetailController', Freecom_incotermDetailController);

    Freecom_incotermDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_incoterm'];

    function Freecom_incotermDetailController($scope, $rootScope, $stateParams, entity, Freecom_incoterm) {
        var vm = this;

        vm.freecom_incoterm = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_incotermUpdate', function(event, result) {
            vm.freecom_incoterm = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
