(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('measure-unit-concept', {
            parent: 'entity',
            url: '/measure-unit-concept',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.measure_unit_concept.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/measure-unit-concept/measure-unit-concepts.html',
                    controller: 'Measure_unit_conceptController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('measure_unit_concept');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('measure-unit-concept-detail', {
            parent: 'entity',
            url: '/measure-unit-concept/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.measure_unit_concept.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/measure-unit-concept/measure-unit-concept-detail.html',
                    controller: 'Measure_unit_conceptDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('measure_unit_concept');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Measure_unit_concept', function($stateParams, Measure_unit_concept) {
                    return Measure_unit_concept.get({id : $stateParams.id});
                }]
            }
        })
        .state('measure-unit-concept.new', {
            parent: 'measure-unit-concept',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/measure-unit-concept/measure-unit-concept-dialog.html',
                    controller: 'Measure_unit_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('measure-unit-concept', null, { reload: true });
                }, function() {
                    $state.go('measure-unit-concept');
                });
            }]
        })
        .state('measure-unit-concept.edit', {
            parent: 'measure-unit-concept',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/measure-unit-concept/measure-unit-concept-dialog.html',
                    controller: 'Measure_unit_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Measure_unit_concept', function(Measure_unit_concept) {
                            return Measure_unit_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('measure-unit-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('measure-unit-concept.delete', {
            parent: 'measure-unit-concept',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/measure-unit-concept/measure-unit-concept-delete-dialog.html',
                    controller: 'Measure_unit_conceptDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Measure_unit_concept', function(Measure_unit_concept) {
                            return Measure_unit_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('measure-unit-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
